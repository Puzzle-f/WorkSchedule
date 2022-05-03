package com.example.workschedule.domain

class GetAllTrainsListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllTrainsList()
}