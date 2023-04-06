package com.example.workschedule.data.database.driver

import androidx.room.*

@Dao
interface DriverDao {
    // Получить весь список машинистов
    @Query("SELECT * FROM DriverEntity ORDER BY surname")
    suspend fun getAllDrivers(): List<DriverEntity>
    // Получить машиниста по id
    @Query("SELECT * FROM DriverEntity WHERE id LIKE :driverId")
    suspend fun getDriverById(driverId: Int): DriverEntity
    // Сохранить нового машиниста
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveDriver(driver: DriverEntity)
    @Update
    suspend fun updateDriver(driver: DriverEntity)
    // Сохранить список машинистов
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveDriverList(driverList: List<DriverEntity>)
    // Удалить машиниста
    @Query("DELETE FROM DriverEntity WHERE id = :driverId")
    suspend fun deleteDriverById(driverId: Int)
    // Удалить всех машинистов
    @Query("DELETE FROM DriverEntity")
    suspend fun deleteAllDrivers()
}