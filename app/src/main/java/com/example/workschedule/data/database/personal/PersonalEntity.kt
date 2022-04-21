package com.example.workschedule.data.database.personal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
class PersonalEntity(

    @field:PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @ColumnInfo(name = "second_name")
    val secondName: String,
    @ColumnInfo(name = "third_name")
    val thirdName: String?,
    @ColumnInfo(name = "hours_worked")
    val hoursWorked: Int?,
    @ColumnInfo(name = "if_work")
    val ifWork: Boolean,
//    если не вышло необходимое время отдыха после поездки
    @ColumnInfo(name = "if_resting")
    val ifResting: Boolean,
//    @ColumnInfo(name = "permissions")
//    val permissions: HashMap<String, Boolean>
)