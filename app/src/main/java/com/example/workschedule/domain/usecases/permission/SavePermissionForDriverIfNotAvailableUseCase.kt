package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Permission

class SavePermissionForDriverIfNotAvailableUseCase(
    val repository: DomainRepository
) {
    suspend fun execute(permission: Permission) = repository.addPermToDriverIfNotAvailable(permission)
}