package com.example.workschedule.domain

class GetTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainNumber: Int) = repository.getTrain(trainNumber)
}