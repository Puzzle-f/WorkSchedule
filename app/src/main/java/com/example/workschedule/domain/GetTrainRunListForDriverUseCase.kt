package com.example.workschedule.domain

import com.example.workschedule.domain.models.Driver

class GetTrainRunListForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.getTrainRunListForDriverId(driverId)

}