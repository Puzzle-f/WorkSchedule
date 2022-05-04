package com.example.workschedule.domain

import com.example.workschedule.domain.models.DomainDriverModel
import com.example.workschedule.domain.models.DomainTrainModel
import com.example.workschedule.domain.models.DomainTrainRunModel

interface DomainRepository {

    suspend fun getAllTrainsRunList(): List<DomainTrainRunModel>
    suspend fun getTrainRun(trainRunId: Int): DomainTrainRunModel
    suspend fun getAllTrainsList(): List<DomainTrainModel>
    suspend fun getAllDriversList(): List<DomainDriverModel>
    suspend fun saveTrainRun(trainRunModel: DomainTrainRunModel)
    suspend fun getTrainRunListForDriver(driverId: Int): List<DomainTrainRunModel>
    suspend fun getDriver(driverId: Int): DomainDriverModel
    suspend fun saveDriver(domainDriverModel: DomainDriverModel)
    suspend fun getTrain(trainNumber: Int): DomainTrainModel
    suspend fun saveTrain(domainTrainModel: DomainTrainModel)
}