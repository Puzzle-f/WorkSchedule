package com.example.workschedule.data.database.weekend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.workschedule.data.database.driver.DriverEntity

/**
 * класс описывает выходной день машиниста
 * @param driverId id машиниста
 * @param date время создания статуса
 * @param status татус занятости (на выходном - 4, не на выходном - 44)
 * */

@Entity(
    primaryKeys = ["driver_id", "date"],
    foreignKeys = [ForeignKey(
        entity = DriverEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("driver_id"),
        onDelete = CASCADE
    )]
)
data class WeekendStatusEntity(
    @field:ColumnInfo(name = "driver_id")
    val driverId: Int,
    @field:ColumnInfo(name = "date")
    val date: Long,
    @field:ColumnInfo(name = "status")
    val status: Int
)
