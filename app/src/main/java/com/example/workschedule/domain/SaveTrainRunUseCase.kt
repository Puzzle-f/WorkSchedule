package com.example.workschedule.domain

import com.example.workschedule.domain.models.DomainTrainRunModel

class SaveTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunModel: DomainTrainRunModel) = repository.saveTrainRun(trainRunModel)
}