package com.example.workschedule.data.database.weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workschedule.domain.models.Weekend
import java.time.LocalDateTime

@Dao
interface WeekendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeekend(weekend: WeekendEntity)
    @Query("SELECT * FROM WeekendEntity WHERE driver_id LIKE :idDriver AND date >= :dateTime")
    suspend fun getAllWeekendsForDriver(idDriver: Int, dateTime: Long): List<WeekendEntity>

}