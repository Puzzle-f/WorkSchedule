package com.example.workschedule.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workschedule.data.db.driver.DriverDao
import com.example.workschedule.data.db.driver.DriverEntity
import com.example.workschedule.data.db.train.TrainDao
import com.example.workschedule.data.db.train.TrainEntity
import com.example.workschedule.data.db.trainrun.TrainRunDao
import com.example.workschedule.data.db.trainrun.TrainRunEntity
import com.example.workschedule.data.entities.TrainRun

@Database(entities = arrayOf(DriverEntity::class, TrainEntity::class, TrainRunEntity::class), version = 1, exportSchema = false)
@TypeConverters(DatabaseTypeConverter::class)
abstract class DriverDataBase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun trainDao(): TrainDao
    abstract fun trainRunDao(): TrainRunDao
}
