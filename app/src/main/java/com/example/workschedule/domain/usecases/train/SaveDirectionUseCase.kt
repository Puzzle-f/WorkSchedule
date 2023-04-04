package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Direction

class SaveDirectionUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(direction: Direction) = repository.saveDirection(direction)
}