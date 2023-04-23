package com.example.workschedule.domain

import com.example.workschedule.domain.models.*
import java.security.Permissions
import java.time.LocalDateTime

interface DomainRepository {
    suspend fun getAllTrainsRunList(): List<TrainRun>
    suspend fun getTrainRun(trainRunId: Int): TrainRun
    suspend fun saveTrainRun(trainRun: TrainRun)
    suspend fun updateTrainRun(trainRun: TrainRun)
    suspend fun deleteTrainRun(trainRunId: Int)
    suspend fun deleteAllTrainRuns()
    suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun>
    suspend fun saveTrainRunList(trainRunList: List<TrainRun>)
    suspend fun clearDriverForTrainRun(driverId: Int)

    suspend fun getAllDriversList(): List<Driver>
    suspend fun getDriver(driverId: Int): Driver?
    suspend fun saveDriver(driver: Driver)
    suspend fun saveDriverList(driverList: List<Driver>)
    suspend fun deleteDriver(driverId: Int)
    suspend fun deleteAllDriversList()
    suspend fun getAllTrainsList(): List<Direction>
    suspend fun updateDriver(driver: Driver)

    suspend fun getDirection(directionId: Int): Direction
    suspend fun saveDirection(direction: Direction)
    suspend fun deleteDirection(trainId: Int)

    suspend fun addPermission(permission: Permission)
    suspend fun getPermissionsForDriver(idDriver: Int): List<Permission>
    suspend fun deletePermission(permission: Permission)

    suspend fun getWeekends(idDriver: Int, dateTime: Long): List<Weekend>
    suspend fun saveWeekend(weekend: Weekend)
    suspend fun getDriverByPersonalNumberAndSurname(personalNumber: Int, surname: String): Driver

}