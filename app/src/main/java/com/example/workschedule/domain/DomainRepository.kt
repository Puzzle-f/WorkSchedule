package com.example.workschedule.domain

import com.example.workschedule.domain.models.DomainDriverModel
import com.example.workschedule.domain.models.DomainTrainModel
import com.example.workschedule.domain.models.DomainTrainRunModel
import kotlinx.coroutines.flow.Flow

interface DomainRepository {

    //MainFragment
    fun getAllTrainsRunList(): Flow<List<DomainTrainRunModel>>

    //EditTrainRunFragment
    suspend fun getTrainRun(trainRunId: Int): DomainTrainRunModel
    suspend fun getAllTrainsList(): List<DomainTrainModel>
    suspend fun getAllDriversList(): List<DomainDriverModel>
    suspend fun saveTrainRun(trainRunModel: DomainTrainRunModel)

    //DriversFragment
    //getAllDriversList

    //DriverInfoFragment
    fun getTrainRunListForDriver(driverId: Int): Flow<List<DomainTrainRunModel>>

    //DriverEditFragment
    suspend fun getDriver(driverId: Int): DomainDriverModel
    //getAllTrainsList
    suspend fun saveDriver(domainDriverModel: DomainDriverModel)

    //TrainsFragment
    //getAllTrainsList

    //TrainsEditFragment
    suspend fun getTrain(trainNumber: Int): DomainTrainModel
    suspend fun saveTrain(domainTrainModel: DomainTrainModel)
}