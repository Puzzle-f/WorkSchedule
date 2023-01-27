package com.example.workschedule.data.database.trainrun

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface TrainRunDao {
    //    Получить все поездки (добавить к запросу " ORDER BY startTime")
    @Query("SELECT * FROM TrainRunEntity")
    suspend fun getAllTrainRuns(): List<TrainRunEntity>

    //    Получить поездку по номеру id поездки
    @Query("SELECT * FROM TrainRunEntity WHERE id LIKE :trainRunId")
    suspend fun getTrainRunById(trainRunId: Int): TrainRunEntity

    //    Получить поездки по номеру id машиниста (добавить к запросу " ORDER BY startTime")
    @Query("SELECT * FROM TrainRunEntity WHERE driverId LIKE :driverId")
    suspend fun getTrainRunByDriverId(driverId: Int): List<TrainRunEntity>

    //    Получить список поездок для указанного машиниста после указанного времени
    @Query("SELECT * FROM TrainRunEntity WHERE driverId LIKE :driverId AND startTime + travelTime + travelRestTime + backTravelTime >= :date")
    suspend fun getTrainRunByDriverIdAfterDate(driverId: Int, date: Long): List<TrainRunEntity>

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