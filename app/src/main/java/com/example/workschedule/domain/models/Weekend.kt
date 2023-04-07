package com.example.workschedule.domain.models


/**
 * класс описывает выходной день машиниста
 * @param driverId id машиниста
 * */

data class Weekend(
    val driverId: Int,
    val date: Long
)