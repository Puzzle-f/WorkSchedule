package com.example.workschedule.data.database.direction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id", "direction"])
data class DirectionEntity(
    @field:ColumnInfo(name = "id")
    var id: Int,
    @field:ColumnInfo(name = "direction")
    val direction: String

)