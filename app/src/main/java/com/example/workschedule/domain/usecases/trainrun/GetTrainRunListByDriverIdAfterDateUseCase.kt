package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository
import java.time.LocalDateTime

class GetTrainRunListByDriverIdAfterDateUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int, date: Long) =
        repository.getTrainRunByDriverIdAfterTime(driverId, date)
}