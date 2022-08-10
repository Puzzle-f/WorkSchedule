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
        database.trainRunDao().getAllTrainRuns().fromDTOListTrainRun

    override suspend fun getTrainRun(trainRunId: Int): TrainRun =
        database.trainRunDao().getTrainRunById(trainRunId).fromDTO

    override suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun> =
        database.trainRunDao().getTrainRunByDriverId(driverId).fromDTOListTrainRun

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        database.trainRunDao().saveTrainRun(trainRun.toDTO)
    }

    override suspend fun saveTrainRunList(trainRunList: List<TrainRun>) {
        database.trainRunDao().saveTrainRun(*trainRunList.map { it.toDTO }.toTypedArray())
    }

    override suspend fun deleteTrainRun(trainRunId: Int) {
        database.trainRunDao().deleteTrainRunById(trainRunId)
    }

    override suspend fun deleteAllTrainRuns() {
        database.trainRunDao().deleteAllTrainRuns()
    }

    override suspend fun getAllDriversList(): List<Driver> =
        database.driverDao().getAllDrivers().fromDTOListDriver

    override suspend fun getDriver(driverId: Int): Driver =
        database.driverDao().getDriverById(driverId).fromDTO

    override suspend fun saveDriver(driver: Driver) {
        database.driverDao().saveDriver(driver.toDTO)
    }

    override suspend fun saveDriverList(driverList: List<Driver>) {
        database.driverDao().saveDriverList(driverList.toDTOListDriver)
    }

    override suspend fun deleteDriver(driverId: Int) {
        database.driverDao().deleteDriverById(driverId)
    }

    override suspend fun deleteAllDriversList() {
        database.driverDao().deleteAllDrivers()
    }

    override suspend fun getAllTrainsList(): List<Train> =
        database.trainDao().getAllTrains().fromDTOListTrain

    override suspend fun getTrain(trainId: Int): Train =
        database.trainDao().getTrainById(trainId).fromDTO

    override suspend fun saveTrain(train: Train) {
        database.trainDao().saveTrain(train.toDTO)
    }

    override suspend fun deleteTrain(trainId: Int) {
        database.trainDao().deleteTrainById(trainId)
    }
}