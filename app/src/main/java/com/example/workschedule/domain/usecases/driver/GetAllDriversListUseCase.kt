package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository

class GetAllDriversListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllDriversList()
}