package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun

class SaveTrainRunListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRun: List<TrainRun>) = repository.saveTrainRunList(trainRun)
}