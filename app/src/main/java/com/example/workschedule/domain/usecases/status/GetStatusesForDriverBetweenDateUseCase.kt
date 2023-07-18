package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Status

class GetStatusesForDriverBetweenDateUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(driverId: Int, dateStart: Long, dateEnd: Long) =
        repository.getStatusesForDriverBetweenDate(driverId, dateStart, dateEnd)

}