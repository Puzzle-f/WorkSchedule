package com.example.workschedule.domain

class GetTrainRunListForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int) = repository.getTrainRunListForDriverId(driverId)
}