package com.example.workschedule.domain

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun

interface DomainRepository {
    suspend fun getAllTrainsRunList(): List<TrainRun>
    suspend fun getTrainRun(trainRunId: Int): TrainRun
    suspend fun saveTrainRun(trainRun: TrainRun)
    suspend fun deleteTrainRun(trainRunId: Int)
    suspend fun deleteAllTrainRuns()
    suspend fun getAllDriversList(): List<Driver>
    suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun>
    suspend fun getDriver(driverId: Int): Driver?
    suspend fun saveDriver(driver: Driver)
    suspend fun saveDriverList(driverList: List<Driver>)
    suspend fun deleteDriver(driverId: Int)
    suspend fun deleteAllDriversList()
    suspend fun getAllTrainsList(): List<Train>
    suspend fun getTrain(trainId: Int): Train
    suspend fun saveTrain(train: Train)
    suspend fun deleteTrain(trainId: Int)
    suspend fun saveTrainRunList(trainRunList: List<TrainRun>)
}