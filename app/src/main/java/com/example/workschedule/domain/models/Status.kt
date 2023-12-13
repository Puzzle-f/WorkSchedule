package com.example.workschedule.domain.models


/**
 * содержит статус работы машиниста
 * @param idDriver - id машиниста
 * @param date - дата и время создания статуса
 * @param status - состояние:
*                   1- в поездке
 *                  2 — отдых после поездки
 *                  3 — в ожидании работы
 *                  3 — в ожидании работы (может быть 2 статуса "3": второй с countNight=0, после необходимого отдыха  )
 * @param countNight - счетчик отработанных ночей подряд на данный момент
 * @param workedTime - общее отработанное машинистом время за месяц
 * @param idBlock - id текущего блока для данного статуса (или null, если на отдыхе)
 * */

data class Status(
    val idDriver: Int,
    val date: Long,
    val status: Int,
    val countNight: Int,
    val workedTime: Long,
    val idBlock: Int?
)