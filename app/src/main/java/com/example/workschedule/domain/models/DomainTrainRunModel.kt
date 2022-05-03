package com.example.workschedule.domain.models

import java.time.LocalDateTime

data class DomainTrainRunModel(
    var id: Int,
    var trainNumber: Int,
    var driverId: Int,
    var startTime: LocalDateTime,
    var travelTime: Long,
    var travelRestTime: Long,
    var backTravelTime: Long
)