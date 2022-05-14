package com.example.workschedule.domain

class GetTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainId: Int) = repository.getTrain(trainId)
}