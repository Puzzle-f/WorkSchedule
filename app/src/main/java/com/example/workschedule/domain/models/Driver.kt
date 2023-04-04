package com.example.workschedule.domain.models

/**
 * Класс Driver определяет сущность машиниста.
 *
 * @param id идентификатор машиниста
 * @param personnelNumber табельный номер машиниста
 * @param surname фамилия машиниста
 * @param name имя машиниста
 * @param patronymic отчество машиниста
 */
data class Driver(
    val id: Int,
    val personnelNumber: Int,
    val surname: String,
    val name: String,
    val patronymic: String
)