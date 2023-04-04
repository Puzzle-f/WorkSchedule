package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Permission

class SavePermissionsUseCase(
    val repository: DomainRepository
) {
    suspend fun execute(permissions: List<Permission>) = repository.savePermissions(permissions)
}