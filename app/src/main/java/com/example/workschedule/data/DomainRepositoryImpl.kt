package com.example.workschedule.data

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.driverList
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.trainList
import com.example.workschedule.domain.trainRunList

class DomainRepositoryImpl(
    private val dataBase: ScheduleDataBase
) : DomainRepository {

    override suspend fun getAllTrainsRunList(): List<TrainRun> = trainRunList

    override suspend fun getTrainRun(trainRunId: Int): TrainRun {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrainRun(trainRunId: Int) {
        // todo
    }

    override suspend fun getAllTrainsList(): List<Train> = trainList

    override suspend fun getAllDriversList(): List<Driver> =
        dataBase.driverDao().all().map {
            Driver(it.id, it.surname, it.name, it.patronymic, it.workedTime, it.totalTime, it.accessTrainsId )
        }

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainRunListForDriver(driverId: Int): List<TrainRun> {
        TODO("Not yet implemented")
    }

    override suspend fun getDriver(driverId: Int): Driver? =
        driverList.find { it.id == driverId }

    override suspend fun deleteDriver(driverId: Int) {
        dataBase.driverDao().delete(driverId)
    }

    override suspend fun saveDriver(driver: Driver) {
        dataBase.driverDao().saveOrChange(
            DriverEntity(
                driver.id,
                driver.name,
                driver.surname,
                driver.patronymic,
                driver.workedTime,
                driver.totalTime,
                driver.accessTrainsId
            )
        )
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