package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository

class DeleteDirectionUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainId: Int) = repository.deleteTrain(trainId)
}