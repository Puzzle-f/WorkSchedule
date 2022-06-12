package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class GetTrainRunListForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.getTrainRunListForDriverId(driverId)
}