package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class ClearDriverForTrainRunBetweenDateUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(idDriver: Int, dateFirst: Long, dateLast: Long) =
        repository.getStatusesForDriverBetweenDate(idDriver, dateFirst, dateLast)
}