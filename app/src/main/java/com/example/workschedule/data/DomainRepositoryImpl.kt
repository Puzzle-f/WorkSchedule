package com.example.workschedule.data

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.DomainDriverModel
import com.example.workschedule.domain.models.DomainTrainModel
import com.example.workschedule.domain.models.DomainTrainRunModel

class DomainRepositoryImpl(
    private val databaseRepository: DatabaseRepository
) : DomainRepository {

    override suspend fun getAllTrainsRunList(): List<DomainTrainRunModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainRun(trainRunId: Int): DomainTrainRunModel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTrainsList(): List<DomainTrainModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDriversList(): List<DomainDriverModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrainRun(trainRunModel: DomainTrainRunModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainRunListForDriver(driverId: Int): List<DomainTrainRunModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getDriver(driverId: Int): DomainDriverModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveDriver(domainDriverModel: DomainDriverModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getTrain(trainNumber: Int): DomainTrainModel {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrain(domainTrainModel: DomainTrainModel) {
        TODO("Not yet implemented")
    }
}