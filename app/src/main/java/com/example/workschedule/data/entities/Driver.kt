package com.example.workschedule.data.entities

/**
 * Класс Driver определяет сущность машиниста.
 *
 * @param id идентификатор машиниста
 * @param name имя машиниста
 * @param surname фамилия машиниста
 * @param patronymic отчество машиниста
 * @param workedTime отработано часов (в millis)
 * @param totalTime всего рабочих часов (в millis)
 * @param accessTrainsId список доступных поездов (направлений)
 */
data class Driver(
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String,
    var workedTime: Long,
    var totalTime: Long,
    var accessTrainsId: List<Int>
)