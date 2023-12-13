package com.example.workschedule.domain.usecases.distraction

import com.example.workschedule.domain.DomainRepository

class DeleteAllDistractionsForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(idDriver: Int) = repository.deleteAllDistractionsForDriver(idDriver)
}