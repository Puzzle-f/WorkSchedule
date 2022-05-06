package com.example.workschedule.domain

import com.example.workschedule.domain.models.DomainDriverModel

class SaveDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(domainDriverModel: DomainDriverModel) = repository.saveDriver(domainDriverModel)
}