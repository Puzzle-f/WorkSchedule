package com.example.workschedule.domain

class DeleteTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainNumber: Int) = repository.deleteTrain(trainNumber)
}