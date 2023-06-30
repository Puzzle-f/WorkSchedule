package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.settings.PLANNING_HORIZON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FindDriverUseCase(
    private val findDriverBeforeHorizonUseCase: FindDriverBeforeHorizonUseCase,
    private val findDriverAfterHorizonUseCase: FindDriverAfterHorizonUseCase
) {

    suspend fun execute(trainRun: TrainRun) =
        coroutineScope {
            launch (Dispatchers.IO){
//                if(trainRun.startTime <= trainRun.startTime + PLANNING_HORIZON){
//                    findDriverBeforeHorizonUseCase.execute(trainRun)
//                } else
                    findDriverAfterHorizonUseCase.execute(trainRun)
            }
        }

}