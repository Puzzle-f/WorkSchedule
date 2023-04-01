package com.example.workschedule.data.database.bindblocktotrainrun

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class BindBlockEntity(
    @field:ColumnInfo(name = "id_block")
    val idBlock: Int,
    @field:ColumnInfo(name = "id_train_run")
    val idTrainRun: Int
)
