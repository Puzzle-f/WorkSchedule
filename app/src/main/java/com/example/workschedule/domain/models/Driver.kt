package com.example.workschedule.domain.models

/**
 * Класс Driver определяет сущность машиниста.
 *
 * @param id идентификатор машиниста
 * @param name имя машиниста
 * @param surname фамилия машиниста
 * @param patronymic отчество машиниста
 * @param workedTime всего рабочих часов  (в millis)
 * @param totalTime время, проведённое на работе (в millis) -пока
 * что этот параметр нам не нужен, но может быть полезен для статистики
 * @param accessTrainsId список доступных машинисту направлений
 */
data class Driver(
    val id: Int,
    val personnelNumber: Int,
    val surname: String,
    val name: String,
    val patronymic: String,
    var workedTime: Long,
    var totalTime: Long,
    var accessTrainsId: List<Int>
)