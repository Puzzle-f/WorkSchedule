package com.example.workschedule.domain.usecases.permission

import com.example.workschedule.domain.DomainRepository

class GetPermissionsForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(idDriver: Int) = repository.getPermissionsForDriver(idDriver)
}