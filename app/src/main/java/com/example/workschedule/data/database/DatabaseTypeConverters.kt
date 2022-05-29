package com.example.workschedule.data.database

import androidx.room.TypeConverter
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.utils.toInt
import com.example.workschedule.utils.toPeriodicity
import java.time.LocalDateTime

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
}

class PeriodicityConverter {
    @TypeConverter
    fun toInt(periodicity: TrainPeriodicity): Int = periodicity.toInt

    @TypeConverter
    fun toPeriodicity(number: Int): TrainPeriodicity = number.toPeriodicity
}