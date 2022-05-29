package com.example.workschedule.data.database.trainrun

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.workschedule.data.database.DateTimeConverter
import com.example.workschedule.data.database.PeriodicityConverter
import com.example.workschedule.domain.models.TrainPeriodicity
import java.time.LocalDateTime

@Entity
@TypeConverters(DateTimeConverter::class, PeriodicityConverter::class)
data class TrainRunEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "trainId")
    val trainId: Int,
    @field:ColumnInfo(name = "trainNumber")
    val trainNumber: Int,
    @field:ColumnInfo(name = "trainDirection")
    val trainDirection: String,
    @field:ColumnInfo(name = "trainPeriodicity")
    val trainPeriodicity: TrainPeriodicity,
    @field:ColumnInfo(name = "driverId")
    val driverId: Int,
    @field:ColumnInfo(name = "driverName")
    val driverName: String,
    @field:ColumnInfo(name = "startTime")
    val startTime: LocalDateTime,
    @field:ColumnInfo(name = "travelTime")
    val travelTime: Long,
    @field:ColumnInfo(name = "travelRestTime")
    val travelRestTime: Long,
    @field:ColumnInfo(name = "backTravelTime")
    val backTravelTime: Long
)