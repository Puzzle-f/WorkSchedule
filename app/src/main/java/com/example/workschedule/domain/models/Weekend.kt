package com.example.workschedule.domain.models


/**
 * класс описывает выходной день машиниста
 * @param driverId id машиниста
 * @param date дата выходного дня
 * @param startWeekend true - начало выходного, false - окончание выходного
 * */

data class Weekend(
    val driverId: Int,
    val date: Long,
    val startWeekend: Boolean
)