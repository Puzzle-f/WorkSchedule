package com.example.workschedule.data.database.distraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DistractionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDistraction(weekend: DistractionEntity)

    @Query("SELECT * FROM DistractionEntity WHERE driver_id LIKE :idDriver ORDER BY date")
    fun getDistractionsForDriver(
        idDriver: Int
    ): Flow<List<DistractionEntity>>

    @Query("SELECT * FROM DistractionEntity WHERE driver_id LIKE :idDriver AND date <= :date ORDER BY date DESC LIMIT 1")
    suspend fun getLastStatusDistraction(idDriver: Int, date: Long): DistractionEntity?

    @Query("DELETE FROM DistractionEntity WHERE driver_id LIKE :idDriver")
    suspend fun deleteAllDistractionsForDriver(idDriver: Int)

    @Query("DELETE FROM DistractionEntity WHERE driver_id LIKE :driverId AND date >= :dateStart AND date <= :dateEnd")
    suspend fun deleteDistraction(driverId: Int, dateStart: Long, dateEnd: Long)
}