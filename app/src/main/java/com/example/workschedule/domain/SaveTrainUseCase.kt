package com.example.workschedule.domain

import com.example.workschedule.domain.models.Train

class SaveTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(train: Train) = repository.saveTrain(train)
}