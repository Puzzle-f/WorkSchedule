package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.domain.usecases.status.DeleteStatusesForDriverAfterDateUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunListByDriverIdAfterDateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecalculateStatusesForDriverAfterTimeUseCase(
    private val deleteStatusesForDriverAfterDateUseCase: DeleteStatusesForDriverAfterDateUseCase,
    private val getTrainRunListByDriverIdAfterDateUseCase: GetTrainRunListByDriverIdAfterDateUseCase,
    private val createListStatusForTrainRunUseCase: CreateListStatusForTrainRunUseCase
) {
    suspend fun execute(driverId: Int, date: Long) =
        coroutineScope {
            launch(Dispatchers.IO) {
                deleteStatusesForDriverAfterDateUseCase.execute(driverId, date)
                getTrainRunListByDriverIdAfterDateUseCase.execute(
                    driverId,
                    date
                ).forEach {
                    createListStatusForTrainRunUseCase.execute(it).join()
                }
            }
        }
}