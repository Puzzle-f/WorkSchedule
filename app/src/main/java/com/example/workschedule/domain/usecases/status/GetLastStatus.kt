package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Status
import com.example.workschedule.utils.fromDTO
import com.example.workschedule.utils.toLong
import java.time.LocalDateTime
import javax.net.ssl.SSLEngineResult

class GetLastStatus(
    private val repository: DomainRepository
) {
//    suspend fun execute(driverId: Int, date: LocalDateTime): Status = repository.getLastStatus(driverId, date.toLong()).fromDTO
}