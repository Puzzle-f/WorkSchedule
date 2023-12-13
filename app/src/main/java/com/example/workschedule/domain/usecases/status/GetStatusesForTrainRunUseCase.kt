package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Status

class GetStatusesForTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int): List<Status> =
        repository.getStatusesForTrainRun(trainRunId)
}