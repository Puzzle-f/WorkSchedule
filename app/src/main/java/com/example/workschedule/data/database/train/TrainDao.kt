package com.example.workschedule.data.database.train


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TrainDao {
//    Получить все поезда
    @Query("SELECT * FROM TrainEntity ORDER BY direction")
    suspend fun getAllTrains(): List<TrainEntity>
//    Получить поезд по номеру
    @Query("SELECT * FROM TrainEntity WHERE id LIKE :trainId")
    suspend fun getTrainById(trainId: Int): TrainEntity
//    добавить новый поезд / изменить
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrain(train: TrainEntity)
//    Удалить поезд
    @Query("DELETE FROM TrainEntity WHERE id = :trainId")
    suspend fun deleteTrainById(trainId: Int)
    //    Удалить все поезда
    @Query("DELETE FROM TrainEntity")
    suspend fun deleteAllTrains()
}