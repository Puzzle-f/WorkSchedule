package com.example.workschedule.data.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.Month
import java.util.stream.Collectors
import java.util.*

class DatabaseTypeConverter {
    @TypeConverter
    fun fromListIntToString(listInt: List<Int>): String{
        if (listInt.isEmpty()){
            return ""
        } else {
            return listInt.stream()
                .map { it.toString() }
                .collect(Collectors.joining("/"))
        }
    }

    @TypeConverter
    fun fromStringToListInt(str: String):List<Int>{
        val resultInt = mutableListOf<Int>()
        str.split("/").map {
            for(i in it){
                resultInt.add(i.toInt())
            }
            return resultInt
        }
        return resultInt
    }

    @TypeConverter
    fun toGregorianCalendar(text: String): GregorianCalendar {
        if (text.isEmpty()){
            return GregorianCalendar()
        }
        val dateList: List<String> = text.split(",")
        return GregorianCalendar().apply {
            this.set(Calendar.YEAR, dateList[0].toInt())
            this.set(Calendar.MONTH, dateList[1].toInt())
            this.set(Calendar.DAY_OF_MONTH, dateList[2].toInt())
            this.set(Calendar.HOUR_OF_DAY, dateList[3].toInt())
            this.set(Calendar.MINUTE, dateList[4].toInt())
        }
    }

    @TypeConverter
    fun fromGregorianCalendar(gregorianCalendar: GregorianCalendar): String {
        return "${gregorianCalendar.get(Calendar.YEAR)}," +
                "${gregorianCalendar.get(Calendar.MONTH)}," +
                "${gregorianCalendar.get(Calendar.DAY_OF_MONTH)}," +
                "${gregorianCalendar.get(Calendar.HOUR_OF_DAY)}," +
                "${gregorianCalendar.get(Calendar.MINUTE)}"
    }

    @TypeConverter
    fun localDataTimeToString(localDateTime: LocalDateTime):String{

        return "LocalDateTime Заглушка для конвертера"
    }

    @TypeConverter
    fun stringToLocalDataTime(string: String):LocalDateTime{
//        TODO Заглушка! Исправить!
        return LocalDateTime.of(2022, Month.APRIL, 9, 6, 30)
    }
}