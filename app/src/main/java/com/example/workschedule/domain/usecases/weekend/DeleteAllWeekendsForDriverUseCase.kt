package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository

class DeleteAllWeekendsForDriverUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(idDriver: Int) = repository.deleteAllWeekendsForDriver(idDriver)
}