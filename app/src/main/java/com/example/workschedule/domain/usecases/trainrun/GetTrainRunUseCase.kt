package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class GetTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int) = repository.getTrainRun(trainRunId)
}
