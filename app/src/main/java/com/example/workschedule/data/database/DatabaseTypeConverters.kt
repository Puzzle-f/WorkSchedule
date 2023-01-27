package com.example.workschedule.data.database

import androidx.room.TypeConverter
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.utils.toInt
import com.example.workschedule.utils.toPeriodicity
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class AccessListConverter {
    @TypeConverter
    fun toString(intList: List<Int>): String =
        if (intList.isEmpty()) ""
        else intList.joinToString()

    @TypeConverter
    fun toIntList(string: String): List<Int> =
        string.split(",").map { it.trim().toInt() }
}

class DateTimeConverter {
    @TypeConverter
    fun longToLocalDateTime(date: Long): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(date),
            TimeZone.getDefault().toZoneId()
        )
    @TypeConverter
    fun localDateTimeToLong(date: LocalDateTime): Long =
        date.atZone(TimeZone.getDefault().toZoneId())
            .toInstant().toEpochMilli()
}

class PeriodicityConverter {
    @TypeConverter
    fun toInt(periodicity: TrainPeriodicity): Int = periodicity.toInt

    @TypeConverter
    fun toPeriodicity(number: Int): TrainPeriodicity = number.toPeriodicity
}
