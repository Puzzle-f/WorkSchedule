package com.example.workschedule.data.database.status

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status

@Dao
interface StatusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStatus(status: StatusEntity)

    @Query("SELECT * FROM StatusEntity ORDER BY date")
    suspend fun getAllStatuses(): List<StatusEntity>

    @Query("SELECT * FROM StatusEntity WHERE id_driver LIKE :driverId")
    suspend fun getStatusesForDriver(driverId: Int): List<StatusEntity>

    @Query("SELECT * FROM StatusEntity WHERE id_driver LIKE :driverId AND date <= :dateLong ORDER BY date DESC LIMIT 1")
    suspend fun getLastStatusForDriver(driverId: Int, dateLong: Long): StatusEntity?

    @Query("DELETE FROM StatusEntity WHERE id_driver LIKE :driverId AND date >= :dateTimeLong")
    suspend fun deleteStatusesForDriverAfterDate(driverId: Int, dateTimeLong: Long)
}