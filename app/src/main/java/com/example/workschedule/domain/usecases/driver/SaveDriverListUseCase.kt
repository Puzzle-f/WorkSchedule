package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Driver

class SaveDriverListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverList: List<Driver>) = repository.saveDriverList(driverList)
}