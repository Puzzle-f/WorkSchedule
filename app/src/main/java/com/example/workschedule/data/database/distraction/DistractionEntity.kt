package com.example.workschedule.data.database.distraction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.workschedule.data.database.driver.DriverEntity

/**
 * класс описывает наличие длительного отвлечения машиниста
 * (болезнь, отпуск, отвлечения)
 * @param isDistracted true - если в отвлечении, false - готов к работе
 * */

@Entity(
    primaryKeys = ["driver_id", "date"],
    foreignKeys = [ForeignKey(
        entity = DriverEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("driver_id"),
        onDelete = ForeignKey.CASCADE
    )]
)

data class DistractionEntity(
    @field:ColumnInfo(name = "driver_id")
    val driverId: Int,
    @field:ColumnInfo(name = "date")
    val date: Long,
    @field: ColumnInfo(name = "startWeekend")
    val isDistracted: Boolean
)