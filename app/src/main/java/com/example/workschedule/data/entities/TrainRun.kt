package com.example.workschedule.data.entities

import java.time.LocalDateTime

/**
 * Класс TrainRun определяет сущность поездки(рейса), необходима для составления сетки выездов и
 * заполнения данными о времени, машинистах, и других данных.
 *
 * @param id идентификатор поезда в сетке выездов
 * @param trainNumber номер поезда
 * @param driverId Id машиниста
 * @param startTime время отправления
 * @param travelTime время пути в пункт назначения
 * @param travelRestTime время отдыха перед отправлением обратно
 * @param backTravelTime время пути обратно в пункт отправления
 */
data class TrainRun(
    val id: Int,
    val trainNumber: Int,
    var driverId: Int,
    val startTime: LocalDateTime,
    val travelTime: Long,
    val travelRestTime: Long,
    val backTravelTime: Long
)