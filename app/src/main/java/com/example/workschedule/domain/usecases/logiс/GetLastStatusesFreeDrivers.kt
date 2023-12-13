package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.status.CreateStatusUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.utils.toDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetLastStatusesFreeDrivers(
    private val getDriverIdByPermissionUseCase: GetDriverIdByPermissionUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
    private val createStatusUseCase: CreateStatusUseCase
) {
    suspend fun execute(trainRun: TrainRun): List<StatusEntity?> {
        val statuses = mutableListOf<StatusEntity?>()
        coroutineScope {
            launch(Dispatchers.IO) {
                val driversId = getDriverIdByPermissionUseCase.execute(trainRun.direction)
                driversId.forEach { driverId ->
                    var status = getLastStatusUseCase.execute(driverId, trainRun.startTime)
                    if (status == null) {
                        val statusFirst = Status(
                            driverId,
                            trainRun.startTime,
                            status = 3,
                            countNight = 0,
                            workedTime = 0,
                            null
                        ).toDTO
                        createStatusUseCase.execute(statusFirst)
                        status = statusFirst
                    }
                    if (status.status == 3 && status.countNight + trainRun.countNight <= 2) {
                        statuses.add(status)
                    }
                }
                statuses.sortBy { it?.workedTime }
            }
        }.join()
        return statuses
    }
}