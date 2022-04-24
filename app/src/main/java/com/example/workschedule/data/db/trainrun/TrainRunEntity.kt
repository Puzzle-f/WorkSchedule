package com.example.workschedule.data.db.trainrun

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/** Сущность поездки
 * */

@Entity
data class TrainRunEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int,
    @field:ColumnInfo(name = "trainNumber")
    var trainNumber: Int,
    @field:ColumnInfo(name = "driverId")
    var driverId: Int,
    @field:ColumnInfo(name = "startTime")
    var startTime: LocalDateTime,
    @field:ColumnInfo(name = "travelTime")
    var travelTime: Long,
    @field:ColumnInfo(name = "travelRestTime")
    var travelRestTime: Long,
    @field:ColumnInfo(name = "backTravelTime")
    var backTravelTime: Long
)