package com.example.workschedule.data.database.driver

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
/**
 * @param id табельный номер машиниста
 * @param name имя
 * @param surname фамилия
 * @param patronymic отчество
 * @param workedTime количество отработанных часов
 *
 * */

@Entity
data class DriverEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int,
    @field:ColumnInfo(name = "name")
    var name: String,
    @field:ColumnInfo(name = "surname")
    var surname: String,
    @field:ColumnInfo(name = "patronymic")
    var patronymic: String,
    @field:ColumnInfo(name = "workedTime")
    var workedTime: Long,
    @field:ColumnInfo(name = "totalTime")
    var totalTime: Long,
    @field:ColumnInfo(name = "accessTrainsId")
//    @TypeConverters(DatabaseTypeConverter.class)
    var accessTrainsId: List<Int>
)