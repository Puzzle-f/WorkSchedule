package com.example.workschedule.data.database.personal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = arrayOf("id"), unique = true)))
class PersonalEntity(

//    @field:PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String?,
    @field:PrimaryKey
    @ColumnInfo(name = "second_name")
    val secondName: String,
    @ColumnInfo(name = "third_name")
    val thirdName: String?,
    @ColumnInfo(name = "hours_worked")
    val hoursWorked: Int?
/* список заключений предлагаю слелать отдельной колонкой
* для каждого направления движения с boolean значением,
* пока оставил БД без заключений  */


)