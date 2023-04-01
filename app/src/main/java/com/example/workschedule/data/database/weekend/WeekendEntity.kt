package com.example.workschedule.data.database.weekend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeekendEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "driver_id")
    val driverId: Int,
    @field:ColumnInfo(name = "date")
    val date: Long
)
