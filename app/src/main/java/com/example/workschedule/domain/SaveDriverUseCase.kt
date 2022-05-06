package com.example.workschedule.domain

import com.example.workschedule.data.models.Driver

class SaveDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driver: Driver) = repository.saveDriver(driver)
}