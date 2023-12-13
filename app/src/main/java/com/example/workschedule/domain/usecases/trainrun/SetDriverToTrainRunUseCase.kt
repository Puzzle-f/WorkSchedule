package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class SetDriverToTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int, driverId: Int) = repository.setDriverToTrainRun(trainRunId, driverId)
}