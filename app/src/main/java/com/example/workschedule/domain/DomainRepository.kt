package com.example.workschedule.domain

import com.example.workschedule.data.models.Driver
import com.example.workschedule.data.models.Train
import com.example.workschedule.data.models.TrainRun

interface DomainRepository {

    suspend fun getAllTrainsRunList(): List<TrainRun>
    suspend fun getTrainRun(trainRunId: Int): TrainRun
    suspend fun getAllTrainsList(): List<Train>
    suspend fun getAllDriversList(): List<Driver>
    suspend fun saveTrainRun(trainRun: TrainRun)
    suspend fun getTrainRunListForDriver(driverId: Int): List<TrainRun>
    suspend fun getDriver(driverId: Int): Driver
    suspend fun saveDriver(driver: Driver)
    suspend fun getTrain(trainNumber: Int): Train
    suspend fun saveTrain(train: Train)
}