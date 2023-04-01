package com.example.workschedule.data.database.status

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class StatusEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "id_driver")
    val idDriver: Int,
    @field:ColumnInfo(name = "date")
    val date: LocalDateTime,
    @field:ColumnInfo(name = "status")
    val status: Int,
    @field:ColumnInfo(name = "count_night")
    val countNight: Int,
    @field:ColumnInfo(name = "worked_time")
    val workedTime: Long,
    @field:ColumnInfo(name = "id_block")
    val idBlock: Int?
)