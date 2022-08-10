package com.example.workschedule.data.database.trainrun

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrainRunDao {
    //    Получить все поездки
    @Query("SELECT * FROM TrainRunEntity ORDER BY startTime")
    suspend fun getAllTrainRuns(): List<TrainRunEntity>

    //    Получить поездку по номеру id поездки
    @Query("SELECT * FROM TrainRunEntity WHERE id LIKE :trainRunId")
    suspend fun getTrainRunById(trainRunId: Int): TrainRunEntity

    //    Получить поездку по номеру id машиниста
    @Query("SELECT * FROM TrainRunEntity WHERE driverId LIKE :driverId ORDER BY startTime")
    suspend fun getTrainRunByDriverId(driverId: Int): List<TrainRunEntity>

    //    Сохранить поездку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrainRun(vararg trainRun: TrainRunEntity)
    //  удалить поездку по id
    @Query("DELETE FROM TrainRunEntity WHERE id = :trainRunId")
    suspend fun deleteTrainRunById(trainRunId: Int)
    //    Удалить все поездки TrainRun
    @Query("DELETE FROM TrainRunEntity")
    suspend fun deleteAllTrainRuns()
}