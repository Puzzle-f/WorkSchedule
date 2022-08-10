package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class DeleteAllTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.deleteAllTrainRuns()
}