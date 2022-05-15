package com.example.workschedule.data

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.*

class DomainRepositoryImpl(
    private val database: ScheduleDataBase
) : DomainRepository {

    override suspend fun getAllTrainsRunList(): List<TrainRun> =
        database.trainRunDao().getAllTrainRuns().fromDAOListTrainRun

    override suspend fun getTrainRun(trainRunId: Int): TrainRun =
        database.trainRunDao().getTrainRunById(trainRunId).fromDAO

    override suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun> =
        database.trainRunDao().getTrainRunByDriverId(driverId).fromDAOListTrainRun

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        database.trainRunDao().saveTrainRun(trainRun.toDAO)
    }

    override suspend fun deleteTrainRun(trainRunId: Int) {
        database.trainRunDao().deleteTrainRunById(trainRunId)
    }

    override suspend fun getAllDriversList(): List<Driver> =
        database.driverDao().getAllDrivers().fromDAOListDriver

    override suspend fun getDriver(driverId: Int): Driver =
        database.driverDao().getDriverById(driverId).fromDAO

    override suspend fun saveDriver(driver: Driver) {
        database.driverDao().saveDriver(driver.toDAO)
    }

    override suspend fun deleteDriver(driverId: Int) {
        database.driverDao().deleteDriverById(driverId)
    }

    override suspend fun getAllTrainsList(): List<Train> =
        database.trainDao().getAllTrains().fromDAOListTrain

    override suspend fun getTrain(trainId: Int): Train =
        database.trainDao().getTrainById(trainId).fromDAO

    override suspend fun saveTrain(train: Train) {
        database.trainDao().saveTrain(train.toDAO)
    }

    override suspend fun deleteTrain(trainId: Int) {
        database.trainDao().deleteTrainById(trainId)
    }
}