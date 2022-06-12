package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Train

class SaveTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(train: Train) = repository.saveTrain(train)
}