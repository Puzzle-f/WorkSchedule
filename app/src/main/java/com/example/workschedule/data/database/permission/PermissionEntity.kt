package com.example.workschedule.data.database.permission

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PermissionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @field: ColumnInfo(name = "id_driver")
    val idDriver: Int,
    @field: ColumnInfo(name = "id_direction")
    val idDirection: Int
)