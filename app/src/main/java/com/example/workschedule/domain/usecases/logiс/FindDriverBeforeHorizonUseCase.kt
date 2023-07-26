package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetDriverUseCase
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.status.*
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.toDTO

class FindDriverBeforeHorizonUseCase(
    private val recalculateStatusesForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val getDriverIdByPermissionUseCase: GetDriverIdByPermissionUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
    private val getDriverUseCase: GetDriverUseCase,
    private val createStatusUseCase: CreateStatusUseCase,
    private val updateTrainRunUseCase: UpdateTrainRunUseCase,
    private val getStatusesForTrainRunUseCase: GetStatusesForTrainRunUseCase,
    private val getStatusesForDriverBetweenDateUseCase: GetStatusesForDriverBetweenDateUseCase,
    private val deleteStatusForTrainRunIdUseCase: DeleteStatusForTrainRunIdUseCase
) {
    suspend fun execute(trainRun: TrainRun): List<Driver?> {
        val drivers = mutableListOf<Driver?>()
        val lastStatuses = mutableListOf<StatusEntity?>()
        getDriverIdByPermissionUseCase.execute(trainRun.direction).forEach { driverId ->
            var status = getLastStatusUseCase.execute(driverId, trainRun.startTime)
            if (status == null) {
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
            }
            if (status.status == 3 && status.countNight + trainRun.countNight <= 2)
                lastStatuses.add(status)
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
                recalculateStatusesForDriverAfterTimeUseCase.execute(
                    lastStatus.idDriver,
                    trainRun.startTime
                ).join()
                val rangeStatuses = getStatusesForTrainRunUseCase.execute(trainRun.id)
                val statusesInRange = getStatusesForDriverBetweenDateUseCase.execute(
                    lastStatus.idDriver,
                    dateStart = trainRun.startTime,
                    dateEnd = rangeStatuses.maxOf { it.date })
//  если статусы машиниста в период с начала планируемой поездки до момента
//  окончания отдыха принадлежат только этой поездке (т.е. нет пересечений с последующими поездками)
                if (!statusesInRange.map { it.idBlock }.any { it != trainRun.id })
                    drivers.add(getDriverUseCase.execute(trainRunLoc.driverId))
//  откатываем все изменения назад
                updateTrainRunUseCase.execute(trainRun)
                deleteStatusForTrainRunIdUseCase.execute(trainRun.id)
                recalculateStatusesForDriverAfterTimeUseCase.execute(
                    lastStatus.idDriver,
                    trainRun.startTime
                ).join()
            }
        }
        return drivers
    }
}

