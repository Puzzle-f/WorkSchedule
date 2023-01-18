package com.example.workschedule.data.database

import androidx.room.TypeConverter
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.utils.toInt
import com.example.workschedule.utils.toPeriodicity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAccessor
import java.time.temporal.TemporalField
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
    fun toDate(dateString: String?): LocalDateTime? =
        dateString?.let { LocalDateTime.parse(dateString) }

    @TypeConverter
    fun toDateString(date: LocalDateTime?): String? = date?.toString()

//    @TypeConverter
//    fun toDate(dateLong: Long): LocalDateTime? =
//        LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(dateLong),
//            TimeZone.getDefault().toZoneId()
//        )
//
//    @TypeConverter
//    fun toDateString(date: LocalDateTime): Long =
//        date.atZone(TimeZone.getDefault().toZoneId())
//            .toInstant().toEpochMilli()


}

class PeriodicityConverter {
    @TypeConverter
    fun toInt(periodicity: TrainPeriodicity): Int = periodicity.toInt

    @TypeConverter
    fun toPeriodicity(number: Int): TrainPeriodicity = number.toPeriodicity
}