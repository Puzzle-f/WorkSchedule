package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository

class DeleteStatusesForDriverAfterDateUseCase(
    private val repository: DomainRepository
){
    suspend fun execute(driverId: Int, dateTime: Long) {
        repository.deleteStatusesForDriverAfterDate(driverId, dateTime)
    }
}