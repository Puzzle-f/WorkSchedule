package com.example.workschedule.data.database

import androidx.room.TypeConverter
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