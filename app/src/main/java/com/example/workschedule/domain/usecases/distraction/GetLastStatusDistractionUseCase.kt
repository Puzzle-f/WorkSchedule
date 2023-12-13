package com.example.workschedule.domain.usecases.distraction

import com.example.workschedule.domain.DomainRepository

class GetLastStatusDistractionUseCase(
    private val repository: DomainRepository
) {

    suspend fun execute(driverId: Int, date: Long) = repository.getLastDistractionStatus(driverId, date)
}