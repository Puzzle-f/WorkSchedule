package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Weekend

class GetWeekendsUseCase(
    private val repository: DomainRepository
) {

    suspend fun execute(
        idDriver: Int
    ): List<Weekend> = repository.getWeekends(idDriver)
}