package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun

class DeleteStatusForTrainRunIdUseCse(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int) = repository.deleteStatusForTrainRunIdUseCse(trainRunId)
}