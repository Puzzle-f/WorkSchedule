package com.example.workschedule.domain.usecases.status

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository

class CreateStatusUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(status: StatusEntity) = repository.createStatus(status)
}