package com.example.workschedule.data.database.driver

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.workschedule.data.database.AccessListConverter

@Entity
@TypeConverters (AccessListConverter::class)
data class DriverEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int,
    @field:ColumnInfo(name = "personnelNumber")
    var personnelNumber: Int,
    @field:ColumnInfo(name = "surname")
    var surname: String,
    @field:ColumnInfo(name = "name")
    var name: String,
    @field:ColumnInfo(name = "patronymic")
    var patronymic: String,
    @field:ColumnInfo(name = "workedTime")
    var workedTime: Long,
    @field:ColumnInfo(name = "totalTime")
    var totalTime: Long,
    @field:ColumnInfo(name = "accessTrainsId")
    var accessTrainsId: List<Int>
)