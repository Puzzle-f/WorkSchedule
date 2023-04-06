package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Permission

class DeletePermissionUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(permission: Permission) = repository.deletePermission(permission)
}