package com.example.workschedule.data.database.permission

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import com.example.workschedule.data.database.driver.DriverEntity

//@Entity(primaryKeys = ["id_driver","id_direction"])
@Entity(primaryKeys = ["id_driver","id_direction"],
    foreignKeys = [ForeignKey(
        entity = DriverEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_driver"),
        onDelete = CASCADE
    )]
)
data class PermissionEntity(
    @field: ColumnInfo(name = "id_driver")
    val idDriver: Int,
    @field: ColumnInfo(name = "id_direction")
    val idDirection: Int
)