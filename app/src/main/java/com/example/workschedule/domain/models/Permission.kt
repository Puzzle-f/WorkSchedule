package com.example.workschedule.domain.models

/** заключения машиниста:
 * связывает id машиниста и id его допуска на участок
 * @param idDriver - id машиниста
 * @param idDirection - id направления
 * */

data class Permission(
    val idDriver: Int,
    val idDirection: Int
)