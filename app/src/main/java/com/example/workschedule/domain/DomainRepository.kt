package com.example.workschedule.domain

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.models.*

interface DomainRepository {
//    TrainRun
    suspend fun getAllTrainsRunList(): List<TrainRun>
    suspend fun getTrainRun(trainRunId: Int): TrainRun?
    suspend fun saveTrainRun(trainRun: TrainRun)
    suspend fun updateTrainRun(trainRun: TrainRun)
    suspend fun setDriverToTrainRun(trainRunId: Int, driverId: Int)
    suspend fun deleteTrainRun(trainRunId: Int)
    suspend fun deleteAllTrainRuns()
    suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun>
    suspend fun saveTrainRunList(trainRunList: List<TrainRun>)
    suspend fun clearDriverForAllTrainRun(driverId: Int)
    suspend fun getTrainRunByNumberAndStartTimeUseCase(number: Int, startDate: Long): TrainRun?
    suspend fun getTrainRunByDriverIdAfterTime(driverId: Int, dateTime: Long): List<TrainRun>
    suspend fun clearDriverForTrainRunUseCase(trainRunId: Int)
    suspend fun clearDriverForTrainRunAfterDate(dateTime: Long)
    suspend fun clearDriverForTrainRunBetweenDate(idDriver: Int, dateFirst: Long, dateLast: Long)

//    Driver
    suspend fun getAllDriversList(): List<Driver>
    suspend fun getDriver(driverId: Int): Driver?
    suspend fun getDriversByPermission(permissionId: Int): List<Int>
    suspend fun saveDriver(driver: Driver)
    suspend fun saveDriverList(driverList: List<Driver>)
    suspend fun deleteDriver(driverId: Int)
    suspend fun deleteAllDriversList()
    suspend fun updateDriver(driver: Driver)
    suspend fun getDriverByPersonalNumberAndSurname(personalNumber: Int, surname: String): Driver

//    Direction
    suspend fun getAllTrainsList(): List<Direction>
    suspend fun getDirection(directionId: Int): Direction
    suspend fun saveDirection(direction: Direction)
    suspend fun deleteDirection(trainId: Int)

//    Permission
    suspend fun addPermission(permission: Permission)
    suspend fun getPermissionsForDriver(idDriver: Int): List<Permission>
    suspend fun deletePermission(permission: Permission)

//    WeekendStatus
    suspend fun getWeekends(idDriver: Int,
//                            dateTimeBeginningOfMonth: Long,
//                            endOfMonth: Long
                            ): List<Weekend>
    suspend fun saveWeekend(weekend: Weekend)
    suspend fun deleteWeekend(driverId: Int, startTime: Long, endTime: Long)
    suspend fun deleteAllWeekendsForDriver(idDriver: Int)

//    Status
    suspend fun getLastStatus(driverId: Int, date: Long): StatusEntity?
    suspend fun getStatusesForTrainRun(trainRunId: Int): List<Status>
    suspend fun getStatusesForDriverBetweenDate(driverId: Int, dateStart: Long, dateEnd: Long):List<Status>?
    suspend fun createStatus(status: StatusEntity)
    suspend fun deleteStatusesForDriverAfterDate(driverId: Int, dateTime: Long)
    suspend fun deleteStatusesAfterDate(date: Long)
    suspend fun deleteStatusForTrainRunId(trainRunId: Int)
}