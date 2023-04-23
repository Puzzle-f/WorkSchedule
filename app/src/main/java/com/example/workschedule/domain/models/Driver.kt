package com.example.workschedule.domain.models

/**
 * Класс Driver определяет сущность машиниста.
 *
 * @param id идентификатор машиниста
 * @param personalNumber табельный номер машиниста
 * @param surname фамилия машиниста
 * @param name имя машиниста
 * @param patronymic отчество машиниста
 */
data class Driver(
    val id: Int,
    val personalNumber: Int,
    val surname: String,
    val name: String,
    val patronymic: String
)