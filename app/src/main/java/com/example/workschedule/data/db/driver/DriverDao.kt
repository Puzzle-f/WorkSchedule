package com.example.workschedule.data.db.driver

import androidx.room.*

@Dao
interface DriverDao {
    // Получить весь список машинистов
    @Query("SELECT * FROM DriverEntity")
    suspend fun all(): List<DriverEntity>
    // Получить машиниста по id
    @Query("SELECT * FROM DriverEntity WHERE id LIKE :id")
    suspend fun getDriverById(id: Int): DriverEntity
    // Сохранить нового или изменить машиниста
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrChange(entity: DriverEntity)
    // Удалить машиниста
    @Delete
    suspend fun delete(entity: DriverEntity)
}