package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository

class GetLastStatusWeekendUseCase(
    private val repository: DomainRepository
) {

    suspend fun execute(driverId: Int, date: Long) = repository.getLastWeekendStatus(driverId, date)
}