package com.example.workschedule.data

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.*
import com.example.workschedule.utils.*

class DomainRepositoryImpl(
    private val database: ScheduleDataBase
) : DomainRepository {

    //      TrainRun
    override suspend fun getAllTrainsRunList(): List<TrainRun> =
        database.trainRunDao().getAllTrainRuns().fromDTOListTrainRun

    override suspend fun getTrainRun(trainRunId: Int): TrainRun? =
        database.trainRunDao().getTrainRunById(trainRunId).fromDTO

    override suspend fun getTrainRunListForDriverId(driverId: Int): List<TrainRun> =
        database.trainRunDao().getTrainRunByDriverId(driverId).fromDTOListTrainRun

    override suspend fun saveTrainRun(trainRun: TrainRun) {
        database.trainRunDao().saveTrainRun(trainRun.toDTO)
    }

    override suspend fun updateTrainRun(trainRun: TrainRun) {
        database.trainRunDao().update(trainRun.toDTO)
    }

    override suspend fun saveTrainRunList(trainRunList: List<TrainRun>) {
        database.trainRunDao().saveTrainRun(*trainRunList.map { it.toDTO }.toTypedArray())
    }

    override suspend fun clearDriverForTrainRun(driverId: Int) {
        database.trainRunDao().clearDriverForTrainRun(driverId)
    }

    override suspend fun getTrainRunByNumberAndStartTimeUseCase(number: Int, startDate: Long) =
        database.trainRunDao().getTrainRunByNumberAndStartTimeUseCase(number, startDate).fromDTO

    override suspend fun getTrainRunByDriverIdAfterTime(
        driverId: Int,
        dateTime: Long
    ) = database.trainRunDao().getTrainRunByDriverIdAfterTime(driverId, dateTime).fromDTOListTrainRun

    override suspend fun clearDriverForTrainRunAfterDate(dateTime: Long) {
        database.trainRunDao().clearDriverForTrainRunAfterDate(dateTime)
    }

    override suspend fun deleteTrainRun(trainRunId: Int) {
        database.trainRunDao().deleteTrainRunById(trainRunId)
    }

    override suspend fun deleteAllTrainRuns() {
        database.trainRunDao().deleteAllTrainRuns()
    }

    //      Permission
    override suspend fun addPermission(permission: Permission) {
        database.permissionDao().addPermissionToDriver(permission.toDTO)
    }

    override suspend fun getPermissionsForDriver(idDriver: Int): List<Permission> =
        database.permissionDao().getPermissionsForDriver(idDriver).map { it.fromDto }

    override suspend fun deletePermission(permission: Permission) {
        database.permissionDao().deletePermissionsToDriver(permission.toDTO)
    }

    override suspend fun getDriverByPersonalNumberAndSurname(
        personalNumber: Int,
        surname: String
    ): Driver =
        database.driverDao().getDriverByPersonalNumberAndSurname(personalNumber, surname).fromDTO

    override suspend fun getAvailableDrivers(dateTime: Long): List<Driver> {
        TODO("Not yet implemented")
        database.driverDao()
    }

    //    Status

    override suspend fun getLastStatus(driverId: Int, date: Long) =
        database.statusDao().getLastStatusForDriver(driverId, date)

    override suspend fun getStatusesForTrainRun(trainRunId: Int): List<Status> =
        database.statusDao().getStatusesForTrainRun(trainRunId).fromDTO

    override suspend fun getStatusesForDriverBetweenDate(
        driverId: Int,
        dateStart: Long,
        dateEnd: Long
    ): List<Status> =
        database.statusDao().getStatusesForDriverBetweenDate(driverId, dateStart, dateEnd).fromDTO

    override suspend fun createStatus(status: StatusEntity) =
        database.statusDao().saveStatus(status)

    override suspend fun deleteStatusesForDriverAfterDate(driverId: Int, dateTime: Long) {
        database.statusDao().deleteStatusesForDriverAfterDate(driverId, dateTime)
    }

    override suspend fun deleteStatusesAfterDate(date: Long) {
        database.statusDao().deleteStatusesAfterDate(date)
    }

    override suspend fun deleteStatusForTrainRunId(trainRunId: Int) {
        database.statusDao().deleteStatusForTrainRunId(trainRunId)
    }

    override suspend fun updateDriver(driver: Driver) {
        database.driverDao().updateDriver(driver.toDTO)
    }

    //      Driver

    override suspend fun getAllDriversList(): List<Driver> =
        database.driverDao().getAllDrivers().fromDTOListDriver

    override suspend fun getDriver(driverId: Int): Driver =
        database.driverDao().getDriverById(driverId).fromDTO

    override suspend fun getDriversByPermission(permissionId: Int): List<Int> =
        database.permissionDao().getDriverIdByPermission(permissionId)

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

    //          Direction
    override suspend fun getAllTrainsList(): List<Direction> =
        database.directionDao().getAllDirections().fromDTOListTrain

    override suspend fun getDirection(directionId: Int): Direction =
        database.directionDao().getDirectionById(directionId).fromDTO

    override suspend fun saveDirection(direction: Direction) {
        database.directionDao().saveDirection(direction.toDTO)
    }

    override suspend fun deleteDirection(trainId: Int) {
        database.directionDao().deleteDirectionById(trainId)
    }
}