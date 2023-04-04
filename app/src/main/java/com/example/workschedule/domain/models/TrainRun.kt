package com.example.workschedule.domain.models

/**
 * Класс TrainRun определяет сущность поездки(рейса), необходима для составления сетки выездов и
 * заполнения данными о времени, машинистах, и других данных.
 *
 * @param id идентификатор поезда в сетке выездов
 * @param driverId Id машиниста
 * @param direction -
 * @param startTime время явки у первого блока
 * @param endTime - время окончания работы у последнего блока
 * @param countNight - отработано ночей подряд за текущую поездку
 * @param workTime - отработано часов за текущую поездку
 */

data class TrainRun(
    val id: Int,
    var driverId: Int,
    val direction: Int,
    val startTime: Long,
    val endTime: Long,
    val countNight: Int,
    val workTime: Long
)