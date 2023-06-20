package com.example.workschedule.domain

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.domain.models.*
import java.security.Permissions
import java.time.LocalDateTime

interface DomainRepository {
    suspend fun getAllTrainsRunList(): List<TrainRun>
    suspend fun getTrainRun(trainRunId: Int): TrainRun?
    suspend fun saveTrainRun(trainRun: TrainRun)
    suspend fun updateTrainRun(trainRun: TrainRun)
    suspend fun deleteTrainRun(trainRunId: Int)
    suspend fun deleteAllTrainRuns()
    suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun>
    suspend fun saveTrainRunList(trainRunList: List<TrainRun>)
    suspend fun clearDriverForTrainRun(driverId: Int)
    suspend fun getTrainRunByNumberAndStartTimeUseCase(number: Int, startDate: Long): TrainRun?
    suspend fun getTrainRunByDriverIdAfterTime(driverId: Int, dateTime: Long): List<TrainRun>

    suspend fun getAllDriversList(): List<Driver>
    suspend fun getDriver(driverId: Int): Driver?
    suspend fun getDriversByPermission(permissionId: Int): List<Int>
    suspend fun saveDriver(driver: Driver)
    suspend fun saveDriverList(driverList: List<Driver>)
    suspend fun deleteDriver(driverId: Int)
    suspend fun deleteAllDriversList()
    suspend fun updateDriver(driver: Driver)
    suspend fun getDriverByPersonalNumberAndSurname(personalNumber: Int, surname: String): Driver

    suspend fun getAllTrainsList(): List<Direction>
    suspend fun getDirection(directionId: Int): Direction
    suspend fun saveDirection(direction: Direction)
    suspend fun deleteDirection(trainId: Int)

    suspend fun addPermission(permission: Permission)
    suspend fun getPermissionsForDriver(idDriver: Int): List<Permission>
    suspend fun deletePermission(permission: Permission)

//    suspend fun getWeekends(idDriver: Int, dateTime: Long): List<Weekend>
//    suspend fun saveWeekend(weekend: Weekend)

    suspend fun getLastStatus(driverId: Int, date: Long): StatusEntity?
    suspend fun createStatus(status: StatusEntity)
    suspend fun deleteStatusForTrainRunIdUseCse(trainRunId: Int)
    suspend fun deleteStatusesForDriverAfterDate(driverId: Int, dateTime: Long)
}