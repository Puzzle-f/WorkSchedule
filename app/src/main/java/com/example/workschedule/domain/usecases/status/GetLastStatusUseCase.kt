package com.example.workschedule.domain.usecases.status

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository

class GetLastStatusUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int, date: Long): StatusEntity? =
        repository.getLastStatus(driverId, date)
}