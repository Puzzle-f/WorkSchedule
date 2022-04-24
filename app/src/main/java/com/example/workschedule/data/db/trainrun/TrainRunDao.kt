package com.example.workschedule.data.db.trainrun

import androidx.room.*
import java.util.*

@Dao
interface TrainRunDao {
    //    Получить все поездки
    @Query("SELECT * FROM TrainRunEntity")
    suspend fun allTrainRun(): List<TrainRunEntity>
    //    Получить поезду по номеру id машиниста
    @Query("SELECT * FROM TrainRunEntity WHERE driverId LIKE :driverId")
    suspend fun getTrainRunByNumber(driverId: Int): TrainRunEntity
//    Получить поездку по номеру поезда
    @Query("SELECT * FROM TrainRunEntity WHERE trainNumber LIKE :trainNumber")
    suspend fun getTrainRunByTrainNumber(trainNumber: Int): TrainRunEntity
//    сформировать поездку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun formTrainRun(trainRun: TrainRunEntity)
}