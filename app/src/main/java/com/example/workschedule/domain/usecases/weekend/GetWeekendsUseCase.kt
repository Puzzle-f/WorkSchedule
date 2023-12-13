package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Weekend
import kotlinx.coroutines.flow.Flow

class GetWeekendsUseCase(
    private val repository: DomainRepository
) {

    suspend fun execute(
        idDriver: Int
    ): Flow<List<Weekend>> = repository.getWeekends(idDriver)
}