package com.example.workschedule.data

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.DomainRepository

class DomainRepositoryImpl() : DomainRepository {

    override suspend fun getAllTrainsRunList(): List<TrainRun> {
        //  TODO("Not yet implemented")
        return com.example.workschedule.utils.trainRunList
    }

    override suspend fun getTrainRun(trainRunId: Int): TrainRun {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTrainsList(): List<Train> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDriversList(): List<Driver> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainRunListForDriver(driverId: Int): List<TrainRun> {
        TODO("Not yet implemented")
    }

    override suspend fun getDriver(driverId: Int): Driver {
        TODO("Not yet implemented")
    }

    override suspend fun saveDriver(driver: Driver) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrain(trainNumber: Int): Train {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrain(train: Train) {
        TODO("Not yet implemented")
    }
}