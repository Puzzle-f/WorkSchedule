package com.example.workschedule.domain

import com.example.workschedule.data.models.TrainRun

class SaveTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRun: TrainRun) = repository.saveTrainRun(trainRun)
}