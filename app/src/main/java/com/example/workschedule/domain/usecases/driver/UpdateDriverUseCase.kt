package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Driver

class UpdateDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driver: Driver) = repository.updateDriver(driver)
}