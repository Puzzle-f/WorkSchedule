package com.example.workschedule.data.database.weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workschedule.domain.models.Weekend

@Dao
interface WeekendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeekend(weekend: Weekend)
    @Query("SELECT * FROM WeekendEntity WHERE driverId = idDriver")
    suspend fun getAllWeekendsForDriver(idDriver: Int)

}