package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class GetAvailableDriversUseCase(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
) {
    suspend fun execute(trainRun: TrainRun) =
//        coroutineScope {
//            launch(Dispatchers.IO) {
//
//
//            }
//        }
        getAllDriversListUseCase.execute().filter {
            val lastStatus = getLastStatusUseCase.execute(it.id, trainRun.startTime)
            lastStatus?.status == 3 && lastStatus.countNight + trainRun.countNight <=2
        }
}