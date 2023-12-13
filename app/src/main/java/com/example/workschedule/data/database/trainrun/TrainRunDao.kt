package com.example.workschedule.data.database.trainrun

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TrainRunDao {
    @Query("SELECT * FROM TrainRunEntity ORDER BY start_time")
    fun getAllTrainRuns(): Flow<List<TrainRunEntity>>

    //    Получить поездку по номеру id поездки
    @Query("SELECT * FROM TrainRunEntity WHERE id LIKE :trainRunId")
    suspend fun getTrainRunById(trainRunId: Int): TrainRunEntity

    @Query("SELECT * FROM TrainRunEntity WHERE driver_id LIKE :driverId ORDER BY start_time")
    suspend fun getTrainRunByDriverId(driverId: Int): List<TrainRunEntity>

    @Query("SELECT * FROM TrainRunEntity WHERE driver_id LIKE :driverId AND start_time >= :dateTime ORDER BY start_time")
    suspend fun getTrainRunByDriverIdAfterTime(driverId: Int, dateTime: Long): List<TrainRunEntity>

    @Query("SELECT * FROM TrainRunEntity WHERE number LIKE :number AND start_time LIKE :startTime")
    suspend fun getTrainRunByNumberAndStartTimeUseCase(number: Int, startTime: Long): TrainRunEntity

    //    Сохранить поездку
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrainRun(vararg trainRun: TrainRunEntity)

    @Update
    suspend fun update(trainRun: TrainRunEntity)

    @Query("UPDATE TrainRunEntity SET driver_id = :driverId WHERE id = :trainRunId")
    suspend fun setDriverToTrainRun(trainRunId: Int, driverId: Int)

    //  удалить поездку по id
    @Query("DELETE FROM TrainRunEntity WHERE id = :trainRunId")
    suspend fun deleteTrainRunById(trainRunId: Int)
    //    Удалить все поездки TrainRun
    @Query("DELETE FROM TrainRunEntity")
    suspend fun deleteAllTrainRuns()

    @Query("UPDATE TrainRunEntity SET driver_id = '' WHERE id = :trainRunId")
    suspend fun clearDriverForTrainRun(trainRunId: Int)

    @Query("UPDATE TrainRunEntity SET driver_id = '' WHERE driver_id = :driverId")
    suspend fun clearDriverForAllTrainRun(driverId: Int)

    @Query("UPDATE TrainRunEntity SET driver_id = '' WHERE start_time >= :date AND is_edit_manually == 0")
    suspend fun clearDriverForTrainRunAfterDate(date: Long)

    @Query("UPDATE TrainRunEntity SET driver_id = '' WHERE driver_id LIKE :idDriver AND start_time BETWEEN :dateStart AND :dateEnd")
    suspend fun clearDriverForTrainRunBetweenDate(idDriver: Int, dateStart: Long, dateEnd: Long)
}