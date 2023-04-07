package com.example.workschedule.data.database.trainrun

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.workschedule.data.database.DateTimeConverter
import com.example.workschedule.data.database.PeriodicityConverter
import com.example.workschedule.domain.models.TrainPeriodicity

@Entity
@TypeConverters(PeriodicityConverter::class, DateTimeConverter::class)
data class TrainRunEntity(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "number")
    val number: String,
    @field:ColumnInfo(name = "driver_id")
    var driverId: Int,
    @field:ColumnInfo(name = "direction")
    val direction: Int,
    @field:ColumnInfo(name = "start_time")
    val startTime: Long,
    @field:ColumnInfo(name = "end_time")
    val endTime: Long,
    @field:ColumnInfo(name = "count_night")
    val countNight: Int,
    @field:ColumnInfo(name = "work_time")
    val workTime: Long,
    @field:ColumnInfo(name = "periodicity")
    val periodicity: Int,
    @field:ColumnInfo(name = "is_edit_manually")
    val isEditManually: Boolean,
    @field:ColumnInfo(name = "other_notes")
    val note: String?
)