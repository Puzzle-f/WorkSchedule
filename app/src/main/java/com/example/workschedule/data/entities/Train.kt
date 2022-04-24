package com.example.workschedule.data.entities

/**
 * Класс Train определяет сущность поезда, необходимую для идентификации направления и вывода информации о нём.
 *
 * @param number номер поезда
 * @param direction название направления
 */
data class Train(
    val number: Int,
    val direction: String,
)