package com.example.workschedule.domain.models

import java.util.*

data class DomainTrainModel(
    val id: Int = 0,
    val trainNumber: Int,
    val direction: Int,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    val totalTimeInMillis: Long = stop.timeInMillis - start.timeInMillis,
    val workedTime: Long
)
