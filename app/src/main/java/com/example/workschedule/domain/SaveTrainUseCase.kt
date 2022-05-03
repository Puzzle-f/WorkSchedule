package com.example.workschedule.domain

import com.example.workschedule.domain.models.DomainTrainModel

class SaveTrainUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(domainTrainModel: DomainTrainModel) = repository.saveTrain(domainTrainModel)
}