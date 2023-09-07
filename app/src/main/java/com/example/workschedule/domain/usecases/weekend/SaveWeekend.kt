package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.WeekendStatus

class SaveWeekendUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(weekend: WeekendStatus) = repository.saveWeekend(weekend)
}