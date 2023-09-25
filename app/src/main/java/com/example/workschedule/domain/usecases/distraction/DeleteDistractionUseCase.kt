package com.example.workschedule.domain.usecases.distraction

import com.example.workschedule.domain.DomainRepository

class DeleteDistractionUseCase(private val repository: DomainRepository) {
    suspend fun execute(idDriver: Int, startTime: Long, endTime: Long) =
        repository.deleteDistraction(idDriver, startTime, endTime)
}