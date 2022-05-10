package com.example.workschedule.domain

class DeleteDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.deleteDriver(driverId)
}