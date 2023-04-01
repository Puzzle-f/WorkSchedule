package com.example.workschedule.domain.models

/**
 * класс описывает элемент поездки
 * (следование пассажиром, или поезд в одну сторону),
 * как правило, в одну поездку входит 2 блока
 * @param trainNumber номер поезда
 * @param direction id направления поезда
 * @param startTime явка (начало работы)
 * @param endTime окончание работы
 * @param isPassenger если требуется следовать пассажтром
 * @param countNight количесво ночей, входящее в этот блок работы
 * @param workingTime рабочее время за этот блок
 * */

data class Block(
    val id: Int,
    val trainNumber: Int,
    val direction: Int,
    val startTime: Long,
    val endTime: Long,
    val isPassenger: Boolean = false,
    val countNight: Int,
    val workingTime: Long
)