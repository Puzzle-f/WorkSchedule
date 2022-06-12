package com.example.workschedule.domain.usecases.train

import com.example.workschedule.domain.DomainRepository

class GetTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainId: Int) = repository.getTrain(trainId)
}