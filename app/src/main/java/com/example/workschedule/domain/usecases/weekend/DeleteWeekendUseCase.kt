package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository

class DeleteWeekendUseCase(private val repository: DomainRepository) {
    suspend fun execute(idDriver: Int, startTime: Long, endTime: Long) =
        repository.deleteWeekend(idDriver, startTime, endTime)
}