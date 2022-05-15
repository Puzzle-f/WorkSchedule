package com.example.workschedule.data.database.trainrun

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrainRunDao {
    //    Получить все поездки
    @Query("SELECT * FROM TrainRunEntity")
    suspend fun getAllTrainRuns(): List<TrainRunEntity>

    //    Получить поездку по номеру id поездки
    @Query("SELECT * FROM TrainRunEntity WHERE id LIKE :trainRunId")
    suspend fun getTrainRunById(trainRunId: Int): TrainRunEntity

    //    Получить поезду по номеру id машиниста
    @Query("SELECT * FROM TrainRunEntity WHERE driverId LIKE :driverId")
    suspend fun getTrainRunByDriverId(driverId: Int): List<TrainRunEntity>

    //    сформировать поездку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrainRun(trainRun: TrainRunEntity)

    @Query("DELETE FROM TrainRunEntity WHERE id = :trainRunId")
    suspend fun deleteTrainRunById(trainRunId: Int)

    @Query("DELETE FROM TrainRunEntity")
    suspend fun deleteAllTrainRuns()
}