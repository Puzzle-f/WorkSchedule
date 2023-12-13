package com.example.workschedule.data.database.direction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DirectionDao {
    //    добавить новое направление / изменить
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDirection(direction: DirectionEntity)

    //    добавить новые направления
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDirections(direction: List<DirectionEntity>)

    //    Получить все направления
    @Query("SELECT * FROM DirectionEntity ORDER BY direction")
    suspend fun getAllDirections(): List<DirectionEntity>

    //    Получить направление по номеру
    @Query("SELECT * FROM DirectionEntity WHERE id LIKE :directionId")
    suspend fun getDirectionById(directionId: Int): DirectionEntity

    //    Удалить направление
    @Query("DELETE FROM DirectionEntity WHERE id = :directionId")
    suspend fun deleteDirectionById(directionId: Int)

    //    Удалить все направления
    @Query("DELETE FROM DirectionEntity")
    suspend fun deleteAllDirections()
}