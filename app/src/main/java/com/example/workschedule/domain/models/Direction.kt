package com.example.workschedule.domain.models

/**
 * Класс Train определяет сущность поезда, необходимую для идентификации направления и вывода информации о нём.
 *
 * @param id ID записи поезда
 * @param direction название направления
 */
data class Train(
    val id: Int,
    val direction: String,
)