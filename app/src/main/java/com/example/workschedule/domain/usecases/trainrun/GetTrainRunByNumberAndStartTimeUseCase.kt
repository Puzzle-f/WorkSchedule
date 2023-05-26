package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.utils.toDTO

class GetTrainRunByNumberAndStartTimeUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(number: Int, startTime: Long): TrainRunEntity? = repository.getTrainRunByNumberAndStartTimeUseCase(number, startTime)?.toDTO
}