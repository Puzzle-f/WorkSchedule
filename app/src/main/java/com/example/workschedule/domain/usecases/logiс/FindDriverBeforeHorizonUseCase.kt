package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FindDriverBeforeHorizonUseCase(
    repository: DomainRepository
) {
    suspend fun execute(trainRun: TrainRun) =
        coroutineScope {
            launch(Dispatchers.IO) {



            }
        }
}