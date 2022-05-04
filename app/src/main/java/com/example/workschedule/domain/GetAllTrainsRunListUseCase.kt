package com.example.workschedule.domain

class GetAllTrainsRunListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllTrainsRunList()
}