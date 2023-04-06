package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Permission

class AddPermissionsUseCase(
    val repository: DomainRepository
) {
    suspend fun execute(permissions: Permission) = repository.addPermission(permissions)
}