package com.example.workschedule.domain.models

/**
 * Класс Direction определяет направление поезда
 *
 * @param id ID направления поезда
 * @param name название направления
 */
data class Direction(
    val id: Int,
    val name: String
)