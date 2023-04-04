package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository

class GetDirectionUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(directionId: Int) = repository.getDirection(directionId)
}