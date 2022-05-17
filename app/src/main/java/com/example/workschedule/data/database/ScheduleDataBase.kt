package com.example.workschedule.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workschedule.data.database.driver.DriverDao
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.train.TrainDao
import com.example.workschedule.data.database.train.TrainEntity
import com.example.workschedule.data.database.trainrun.TrainRunDao
import com.example.workschedule.data.database.trainrun.TrainRunEntity

@Database(
    entities = [DriverEntity::class, TrainEntity::class, TrainRunEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ScheduleDataBase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun trainDao(): TrainDao
    abstract fun trainRunDao(): TrainRunDao
}
