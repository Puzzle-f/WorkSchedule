package com.example.workschedule.domain.usecases.weekend

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.WeekendStatus
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toLong
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class GetWeekendsUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(
        idDriver: Int,
        date: Long
    ): List<WeekendStatus> {
        val dateTime = date.toLocalDateTime()
        val firstDayOfMonth = LocalDate.of(dateTime.year, dateTime.month, 1)
        val startTime = LocalDateTime.of(firstDayOfMonth, LocalTime.MIDNIGHT).toLong()
        val lastDayOfMonth =
            LocalDate.of(dateTime.year, dateTime.month, dateTime.month.length(false))
        val endTime = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX).toLong()
        return repository.getWeekends(idDriver, startTime, endTime)
    }
}