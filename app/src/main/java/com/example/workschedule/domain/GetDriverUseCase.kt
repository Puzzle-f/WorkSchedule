package com.example.workschedule.domain

class GetDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.getDriver(driverId)
}