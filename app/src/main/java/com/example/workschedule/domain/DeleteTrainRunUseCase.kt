package com.example.workschedule.domain

class DeleteTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int) = repository.deleteTrainRun(trainRunId)
}
