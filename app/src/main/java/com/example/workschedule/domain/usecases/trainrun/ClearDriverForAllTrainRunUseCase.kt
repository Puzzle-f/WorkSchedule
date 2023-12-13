package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class ClearDriverForAllTrainRunUseCase (
    private val repository: DomainRepository
        ){
    suspend fun execute(driverId: Int) = repository.clearDriverForAllTrainRun(driverId)
}