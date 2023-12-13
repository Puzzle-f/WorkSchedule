package com.example.workschedule.data.database.weekend

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeekend(weekend: WeekendEntity)

    @Query("SELECT * FROM WeekendEntity WHERE driver_id LIKE :idDriver ORDER BY date")
    fun getWeekendsForDriver(
        idDriver: Int
    ): Flow<List<WeekendEntity>>

    @Query("SELECT * FROM WeekendEntity WHERE driver_id LIKE :idDriver AND date <= :date ORDER BY date DESC LIMIT 1")
    suspend fun getLastStatus(idDriver: Int, date: Long): WeekendEntity?

    @Query("DELETE FROM WeekendEntity WHERE driver_id LIKE :idDriver")
    suspend fun deleteAllWeekendsForDriver(idDriver: Int)

    @Query("DELETE FROM WeekendEntity WHERE driver_id LIKE :driverId AND date >= :dateStart AND date <= :dateEnd")
    suspend fun deleteWeekend(driverId: Int, dateStart: Long, dateEnd: Long)
}