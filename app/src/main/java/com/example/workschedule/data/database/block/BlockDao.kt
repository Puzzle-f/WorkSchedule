package com.example.workschedule.data.database.block

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workschedule.domain.models.Block

@Dao
interface BlockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBlock(block: Block)
    @Query("DELETE FROM BlockEntity WHERE id = : id")
    suspend fun deleteBlock(id: Int)
    @Query("SELECT * FROM BlockEntity ORDER BY id")
    suspend fun getAllBlocks()
}