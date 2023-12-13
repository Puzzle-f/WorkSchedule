package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.TrainRun
import kotlinx.coroutines.flow.Flow

class GetAllTrainsRunListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(): Flow<List<TrainRun>> = repository.getAllTrainsRunList()
}