package com.example.workschedule.domain.usecases.logiс

import android.util.Log
import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.status.CreateStatusUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.toDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FindDriverAfterHorizonUseCase(
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val getDriverIdByPermissionUseCase: GetDriverIdByPermissionUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
    private val createStatusUseCase: CreateStatusUseCase,
    private val updateTrainRunUseCase: UpdateTrainRunUseCase
) {
    suspend fun execute(trainRun: TrainRun) =
        coroutineScope {
            launch(Dispatchers.IO) {
                val statuses = mutableListOf<StatusEntity?>()
                getDriverIdByPermissionUseCase.execute(trainRun.direction).forEach { driverId ->
//                    var status: StatusEntity? = null
//                    launch(Dispatchers.IO) {
                      var status = getLastStatusUseCase.execute(driverId, trainRun.startTime)
//                    }.join()

                    if (status == null) {
                        Log.e("", "status = $status")
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
                    if (status.status == 3 && status.countNight + trainRun.countNight <= 2) {
                        statuses.add(status)
                    }
                }
                statuses.sortBy { it?.workedTime }
                Log.e("", "$statuses")
                if (statuses.isNotEmpty()){
                    trainRun.driverId = statuses.first()!!.idDriver
                    updateTrainRunUseCase.execute(trainRun)
                    Log.e("", "$trainRun")
                    recalculateStatusesForForDriverAfterTimeUseCase.execute(
                        trainRun.driverId,
                        trainRun.startTime
                    )
                }
            }
        }
}