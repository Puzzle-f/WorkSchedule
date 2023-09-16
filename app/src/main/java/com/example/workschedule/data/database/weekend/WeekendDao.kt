package com.example.workschedule.data.database.weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeekendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeekend(weekend: WeekendEntity)

    @Query("SELECT * FROM WeekendEntity WHERE driver_id LIKE :idDriver ORDER BY date")
    suspend fun getWeekendsForDriver(
        idDriver: Int
    ): List<WeekendEntity>

    @Query("DELETE FROM WeekendEntity WHERE driver_id LIKE :idDriver")
    suspend fun deleteAllWeekendsForDriver(idDriver: Int)

    @Query("DELETE FROM WeekendEntity WHERE driver_id LIKE :driverId AND date >= :dateStart AND date <= :dateEnd")
    suspend fun deleteWeekend(driverId: Int, dateStart: Long, dateEnd: Long)
}