package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository

class GetDriverIdByPermission(
    private val repository: DomainRepository
) {
    suspend fun execute(permissionId: Int): List<Int> = repository.getDriversByPermission(permissionId)
}