package com.example.workschedule.data.database.status

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.example.workschedule.data.database.DateTimeConverter
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.trainrun.TrainRunEntity

@Entity(primaryKeys = ["id_driver","date", "status"],
    foreignKeys = [ForeignKey(
        entity = DriverEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_driver"),
        onDelete = CASCADE
    ), ForeignKey(
        entity = TrainRunEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_block"),
        onDelete = CASCADE
    )]
)
@TypeConverters(DateTimeConverter::class)
data class StatusEntity(
    @field:ColumnInfo(name = "id_driver")
    val idDriver: Int,
    @field:ColumnInfo(name = "date")
    val date: Long,
    @field:ColumnInfo(name = "status")
    val status: Int,
    @field:ColumnInfo(name = "count_night")
    val countNight: Int,
    @field:ColumnInfo(name = "worked_time")
    val workedTime: Long,
    @field:ColumnInfo(name = "id_block")
    val idBlock: Int?
)