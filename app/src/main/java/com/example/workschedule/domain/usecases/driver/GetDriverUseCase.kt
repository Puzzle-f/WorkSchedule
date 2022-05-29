package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository

class GetDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.getDriver(driverId)
}