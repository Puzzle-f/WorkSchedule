package com.example.workschedule.data.database.personal

import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [PersonalEntity::class], version = 1, exportSchema = false
)
abstract class PersonalDataBase : RoomDatabase() {
    abstract fun mainDao(): PersonalDao
}