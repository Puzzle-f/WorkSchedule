package com.example.workschedule.data.db.train


import androidx.room.*
import java.util.*


@Dao
interface TrainDao {
//    Получить все поезда
    @Query("SELECT * FROM TrainEntity")
    suspend fun allTrain(): List<TrainEntity>
//    Получить все поезда на указанную дату
    @Query("SELECT * FROM TrainEntity WHERE start LIKE :start")
    suspend fun getTrainByDataStart(start: GregorianCalendar): List<TrainEntity>
//    Получить поезд по номеру
    @Query("SELECT * FROM TrainEntity WHERE trainNumber LIKE :trainNumber")
    suspend fun getTrainByNumber(trainNumber: Int): TrainEntity
//    добавить новый поезд / изменить
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrChange(train: TrainEntity)
//    Удалить поезд
    @Delete
    suspend fun delete(train: TrainEntity)
}