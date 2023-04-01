package com.example.workschedule.data.database.block

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BlockEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "train_number")
    val trainNumber: Int,
    @field:ColumnInfo(name = "direction")
    val direction: Int,
    @field:ColumnInfo(name = "start_time")
    val startTime: Long,
    @field:ColumnInfo(name = "end_time")
    val endTime: Long,
    @field:ColumnInfo(name = "is_passenger")
    val isPassenger: Boolean = false,
    @field:ColumnInfo(name = "count_night")
    val countNight: Int,
    @field:ColumnInfo(name = "working_time")
    val workingTime: Long
)