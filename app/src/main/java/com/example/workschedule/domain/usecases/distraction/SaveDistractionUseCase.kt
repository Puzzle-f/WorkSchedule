package com.example.workschedule.domain.usecases.distraction

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Distraction

class SaveDistractionUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(distraction: Distraction) = repository.saveDistraction(distraction)
}