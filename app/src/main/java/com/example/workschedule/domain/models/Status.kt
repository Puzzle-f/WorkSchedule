package com.example.workschedule.domain.models

import java.time.LocalDateTime

/**
 * содержит статус работы машиниста
 * @param idDriver - id машиниста
 * @param date - дата и время создания статуса
 * @param status - состояние:
*                   1- в поездке
 *                  2 — отдых после поездки
 *                  3 — в ожидании работы
 *                  4 — выходной
 *                  5 — больничный
 * @param countNight - счетчик отработанных ночей подряд на данный момент
 * @param workedTime - общее отработанное машинистом время за месяц
 * @param idBlock - id текущего блока для данного статуса (или null, если на отдыхе)
 * */

data class Status(
    val id: Int,
    val idDriver: Int,
    val date: LocalDateTime,
    val status: Int,
    val countNight: Int,
    val workedTime: Long,
    val idBlock: Int?
)