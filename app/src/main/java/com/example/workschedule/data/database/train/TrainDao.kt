package com.example.workschedule.data.database.train


import androidx.room.*
import java.util.*


@Dao
interface TrainDao {
//    Получить все поезда
    @Query("SELECT * FROM TrainEntity")
    suspend fun allTrain(): List<TrainEntity>
//    Получить все поезда на указанную дату
//    @Query("SELECT * FROM TrainEntity WHERE start LIKE :start")
//    suspend fun getTrainByDataStart(start: GregorianCalendar): List<TrainEntity>
//    Получить поезд по номеру
    @Query("SELECT * FROM TrainEntity WHERE id LIKE :trainId")
    suspend fun getTrainById(trainId: Int): TrainEntity
//    добавить новый поезд / изменить
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrChangeTrain(train: TrainEntity)
//    Удалить поезд
    @Query("DELETE FROM TrainEntity WHERE id = :trainId")
    suspend fun deleteTrainById(trainId: Int)
}