package com.example.workschedule.domain

class GetTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int) = repository.getTrainRun(trainRunId)
}
