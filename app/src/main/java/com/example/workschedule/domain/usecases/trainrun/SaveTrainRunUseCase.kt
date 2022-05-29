package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun

class SaveTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRun: TrainRun) = repository.saveTrainRun(trainRun)
}