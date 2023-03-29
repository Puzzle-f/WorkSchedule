package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Direction

class SaveTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(train: Direction) = repository.saveTrain(train)
}