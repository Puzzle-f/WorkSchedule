package com.example.workschedule.domain

class GetAllDriversListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllDriversList()
}