package com.example.workschedule.domain.usecases.distraction

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Distraction
import kotlinx.coroutines.flow.Flow

class GetDistractionUseCase(
    private val repository: DomainRepository
) {

    suspend fun execute(
        idDriver: Int
    ): Flow<List<Distraction>> = repository.getDistractions(idDriver)
}