package com.example.workschedule.data.database.train

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Класс Train определяет сущность поезда, необходимую для идентификации направления и вывода информации о нём.
 *
 * @param trainNumber номер поезда
 * @param direction код направления
 * @param start время начала поездки
 * @param stop время окончания поездки
 * @param totalTimeInMillis время, фактически проведённое в поездке
 * @param workedTime время, считающееся рабочим
 */

@Entity
data class TrainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainNumber: Int,
    val direction: Int,
    val start: GregorianCalendar,
    val stop: GregorianCalendar,
    val totalTimeInMillis: Long = stop.timeInMillis - start.timeInMillis,
    val workedTime: Long
    )