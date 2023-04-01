package com.example.workschedule.domain.models

import java.time.LocalDateTime

/**
 * класс описывает выходной день машиниста
 * @param driverId id машиниста
 * */

data class Weekend(
    val id: Int,
    val driverId: Int,
    val date: LocalDateTime
)