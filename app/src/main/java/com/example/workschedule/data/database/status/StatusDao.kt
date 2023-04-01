package com.example.workschedule.data.database.status

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workschedule.domain.models.Status

@Dao
interface StatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStatus(status: Status)
    @Query("SELECT * FROM StatusEntity ORDER BY id")
    suspend fun getAllStatuses()
    @Query("DELETE FROM StatusEntity WHERE id = id")
    suspend fun deleteStatus(id: Int)
}