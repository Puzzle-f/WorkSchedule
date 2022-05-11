package com.example.workschedule.data

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.driverList
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.trainList
import com.example.workschedule.domain.trainRunList

class DomainRepositoryImpl : DomainRepository {

    override suspend fun getAllTrainsRunList(): List<TrainRun> = trainRunList

    override suspend fun getTrainRun(trainRunId: Int): TrainRun {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrainRun(trainRunId: Int) {
        // todo
    }

    override suspend fun getAllTrainsList(): List<Train> = trainList

    override suspend fun getAllDriversList(): List<Driver> = driverList

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainRunListForDriver(driverId: Int): List<TrainRun> {
        TODO("Not yet implemented")
    }

    override suspend fun getDriver(driverId: Int): Driver {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDriver(driverId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDriver(driver: Driver) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrain(trainNumber: Int): Train =
        trainList.first { it.number == trainNumber }

    override suspend fun deleteTrain(trainNumber: Int) {
        // todo
    }

    override suspend fun saveTrain(train: Train) {
        TODO("Not yet implemented")
    }
}