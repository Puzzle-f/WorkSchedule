package com.example.workschedule.domain.models

import java.time.LocalDateTime

/**
 * Класс TrainRun определяет сущность поездки(рейса), необходима для составления сетки выездов и
 * заполнения данными о времени, машинистах, и других данных.
 *
 * @param id идентификатор поезда в сетке выездов
 * @param trainId ID поезда
 * @param trainNumber номер поезда
 * @param trainDirection направление поезда
 * @param trainPeriodicity периодичность поезда
 * @param driverId Id машиниста
 * @param driverName ФИО машиниста
 * @param startTime время отправления
 * @param travelTime время пути в пункт назначения
 * @param travelRestTime время отдыха перед отправлением обратно
 * @param backTravelTime время пути обратно в пункт отправления
 */
data class TrainRun(
    val id: Int,
    val trainId: Int,
    val trainNumber: Int,
    val trainDirection: String,
    val trainPeriodicity: TrainPeriodicity,
    var driverId: Int,
    var driverName: String,
    val startTime: LocalDateTime,
    val travelTime: Long,
    val travelRestTime: Long,
    val backTravelTime: Long
)