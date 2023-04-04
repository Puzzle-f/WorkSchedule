package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository

class GetAllDirectionsListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllTrainsList()
}