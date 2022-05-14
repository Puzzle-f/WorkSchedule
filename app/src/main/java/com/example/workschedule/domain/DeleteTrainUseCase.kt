package com.example.workschedule.domain

class DeleteTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainId: Int) = repository.deleteTrain(trainId)
}