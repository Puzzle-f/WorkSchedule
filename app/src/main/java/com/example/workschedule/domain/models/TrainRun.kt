package com.example.workschedule.domain.models

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
 * @param startTime время отправления (LocalDateTime, приведённый к Long)
 * @param travelTime время пути в пункт назначения
 * @param travelRestTime время отдыха перед отправлением обратно
 * @param backTravelTime время пути обратно в пункт отправления
 * @param isEditManually если выезд редактировался вручную
 */
data class TrainRun(
    val id: Int,
    val trainId: Int,
    val trainNumber: Int,
    val trainDirection: String,
    val trainPeriodicity: TrainPeriodicity,
    var driverId: Int,
    var driverName: String,
    val startTime: Long,
    val travelTime: Long,
    val travelRestTime: Long,
    val backTravelTime: Long,
    var isEditManually: Boolean
)