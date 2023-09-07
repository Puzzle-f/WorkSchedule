package com.example.workschedule.domain.models


/**
 * класс описывает выходной день машиниста
 * @param driverId id машиниста
 * @param date время создания статуса
 * @param status татус занятости (на выходном - 4, не на выходном - 44)
 * */

data class WeekendStatus(
    val driverId: Int,
    val date: Long,
    val status: Int
)