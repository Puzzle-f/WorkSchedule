package com.example.workschedule.data.database.weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeekendStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeekend(weekend: WeekendStatusEntity)

    @Query("SELECT * FROM WeekendStatusEntity WHERE driver_id LIKE :idDriver AND date >= :dateStart AND date <= :dateEnd")
    suspend fun getAllWeekendsForDriver(idDriver: Int, dateStart: Long, dateEnd: Long): List<WeekendStatusEntity>

}