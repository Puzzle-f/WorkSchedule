package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.status.*
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.toDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FindDriverAfterHorizonUseCase(
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val getDriverIdByPermissionUseCase: GetDriverIdByPermissionUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
    private val createStatusUseCase: CreateStatusUseCase,
    private val updateTrainRunUseCase: UpdateTrainRunUseCase,
    private val getStatusesForTrainRunUseCase: GetStatusesForTrainRunUseCase,
    private val getStatusesForDriverBetweenDateUseCase: GetStatusesForDriverBetweenDateUseCase,
    private val deleteStatusForTrainRunIdUseCase: DeleteStatusForTrainRunIdUseCase
) {
    suspend fun execute(trainRun: TrainRun) =
        coroutineScope {
            launch(Dispatchers.IO) {
                val lastStatuses = mutableListOf<StatusEntity?>()
                getDriverIdByPermissionUseCase.execute(trainRun.direction).forEach { driverId ->
                    var status = getLastStatusUseCase.execute(driverId, trainRun.startTime)
                    if (status == null) {
                        launch {
                            val statusFirst = Status(
                                driverId,
                                trainRun.startTime,
                                status = 3,
                                countNight = 0,
                                workedTime = 0,
                                trainRun.id
                            ).toDTO
                            createStatusUseCase.execute(statusFirst)
                            status = statusFirst
                        }.join()
                    }
                    if (status?.status == 3 && (status!!.countNight  + trainRun.countNight) <= 2) {
                        lastStatuses.add(status)
                    }
                }
                lastStatuses.sortBy { it?.workedTime }
                if (lastStatuses.isNotEmpty()) {
                    lastStatuses.forEach { lastStatus ->
                        val trainRunLoc = TrainRun(
                            trainRun.id, trainRun.number, lastStatus!!.idDriver,
                            trainRun.direction, trainRun.startTime, trainRun.travelTime,
                            trainRun.countNight, trainRun.workTime, trainRun.periodicity,
                            trainRun.isEditManually, trainRun.note
                        )
                        updateTrainRunUseCase.execute(trainRunLoc)
                        recalculateStatusesForForDriverAfterTimeUseCase.execute(
                            lastStatus.idDriver,
                            trainRun.startTime
                        ).join()
                        val rangeStatuses = getStatusesForTrainRunUseCase.execute(trainRun.id)
                        val statusesInRange = getStatusesForDriverBetweenDateUseCase.execute(
                            lastStatus.idDriver,
                            dateStart = trainRun.startTime,
                            dateEnd = rangeStatuses.maxOf { it.date })
//  если статусы машиниста в период с начала планируемой поездки до момента
//  окончания отдыха принадлежат только этой поездке (т.е. нет пересечений с другими поездками)
                        if (!statusesInRange.map { it.idBlock }
                                .any { it != trainRun.id }) return@launch
                        else {
                            updateTrainRunUseCase.execute(trainRun)
                            deleteStatusForTrainRunIdUseCase.execute(trainRun.id)
                            recalculateStatusesForForDriverAfterTimeUseCase.execute(
                                lastStatus.idDriver,
                                trainRun.startTime
                            ).join()
                        }
                    }
                }
            }
        }
}