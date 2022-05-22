package com.example.workschedule.utils

import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.train.TrainEntity
import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import java.util.concurrent.TimeUnit

val Int.hoursToMillis: Long // Экстеншн для перевода интового значения часов в millis
    get() = TimeUnit.HOURS.toMillis(this.toLong())

val Int.minutesToMillis: Long // Экстеншн для перевода интового значения минут в millis
    get() = TimeUnit.MINUTES.toMillis(this.toLong())

// Экстеншн для перевода рабочего времени millis в строку вывода HH:mm
val Long.toTimeString: String
    get() {
        val hours = this / 1000 / 60 / 60
        val minutes = this / 1000 / 60 % 60
        return String.format("%02d:%02d", hours, minutes)
    }

// Экстеншн для перевода рабочего времени millis в строку вывода HH
val Long.toHoursTimeString: String
    get() {
        val hours = this / 1000 / 60 / 60
        return "$hours"
    }

// Экстеншн для перевода строки HH:mm в millis
val String.timeToMillis: Long
    get() = this.split(':')[0].toLong() * 60 * 60 * 1000 + this.split(':')[1].toLong() * 60 * 1000

val List<TrainEntity>.fromDTOListTrain: List<Train> // Экстеншн преобразования списка TrainEntity в Train
    get() = this.map { Train(it.id, it.direction) }

val TrainEntity.fromDTO: Train // Экстеншн преобразования TrainEntity в Train
    get() = Train(this.id, this.direction)

val Train.toDTO: TrainEntity // Экстеншн преобразования Train в TrainEntity
    get() = TrainEntity(this.id, this.direction)

val List<DriverEntity>.fromDTOListDriver: List<Driver> // Экстеншн преобразования списка DriverEntity в Driver
    get() = this.map {
        Driver(
            it.id,
            it.personnelNumber,
            it.surname,
            it.name,
            it.patronymic,
            it.workedTime,
            it.totalTime,
            it.accessTrainsId
        )
    }

val DriverEntity.fromDTO: Driver // Экстеншн преобразования DriverEntity в Driver
    get() = Driver(
        this.id,
        this.personnelNumber,
        this.surname,
        this.name,
        this.patronymic,
        this.workedTime,
        this.totalTime,
        this.accessTrainsId
    )

val Driver.toDTO: DriverEntity // Экстеншн преобразования Driver в DriverEntity
    get() = DriverEntity(
        this.id,
        this.personnelNumber ?: 0,
        this.surname,
        this.name,
        this.patronymic,
        this.workedTime,
        this.totalTime,
        this.accessTrainsId
    )

val List<TrainRunEntity>.fromDTOListTrainRun: List<TrainRun> // Экстеншн преобразования списка TrainRunEntity в TrainRun
    get() = this.map {
        TrainRun(
            it.id,
            it.trainId,
            it.trainNumber,
            it.trainDirection,
            it.driverId,
            it.driverName,
            it.startTime,
            it.travelTime,
            it.travelRestTime,
            it.backTravelTime
        )
    }

val TrainRunEntity.fromDTO: TrainRun // Экстеншн преобразования TrainRunEntity в TrainRun
    get() = TrainRun(
        this.id,
        this.trainId,
        this.trainNumber,
        this.trainDirection,
        this.driverId,
        this.driverName,
        this.startTime,
        this.travelTime,
        this.travelRestTime,
        this.backTravelTime
    )

val TrainRun.toDTO: TrainRunEntity // Экстеншн преобразования TrainRun в TrainRunEntity
    get() = TrainRunEntity(
        this.id,
        this.trainId,
        this.trainNumber,
        this.trainDirection,
        this.driverId,
        this.driverName,
        this.startTime,
        this.travelTime,
        this.travelRestTime,
        this.backTravelTime
    )

val Driver.FIO: String  // Экстеншн для выделения фамилии с инициалами из объекта машиниста
    get() = StringBuilder()
        .append(this.surname + " ")
        .append(if (this.name.isNotBlank()) this.name.first() + ". " else " ")
        .append(if (this.patronymic.isNotBlank()) this.patronymic.first() + "." else "")
        .toString()
