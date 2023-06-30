package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun

class FindDriverBeforeHorizonUseCase(repository: DomainRepository) {
    suspend fun execute(trainRun: TrainRun){}
}