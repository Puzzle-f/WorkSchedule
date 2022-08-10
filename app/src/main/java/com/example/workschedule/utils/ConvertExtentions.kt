package com.example.workschedule.utils

import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.train.TrainEntity
import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import java.time.LocalDateTime
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

val TrainPeriodicity.toInt: Int // Экстеншн для перевода периодичности в целое число
    get() = when (this) {
        TrainPeriodicity.SINGLE -> 0
        TrainPeriodicity.ON_ODD -> 1
        TrainPeriodicity.ON_EVEN -> 2
        TrainPeriodicity.DAILY -> 3
    }

val Int.toPeriodicity: TrainPeriodicity // Экстеншн для перевода целого числа в значение периодичности
    get() = when (this) {
        1 -> TrainPeriodicity.ON_ODD
        2 -> TrainPeriodicity.ON_EVEN
        3 -> TrainPeriodicity.DAILY
        else -> TrainPeriodicity.SINGLE
    }

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

val List<Driver>.toDTOListDriver: List<DriverEntity> // Экстеншн преобразования списка Driver в DriverEntity
    get() = this.map {
        DriverEntity(
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
        this.personnelNumber,
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
            it.trainPeriodicity,
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
        this.trainPeriodicity,
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
        this.trainPeriodicity,
        this.driverId,
        this.driverName,
        this.startTime,
        this.travelTime,
        this.travelRestTime,
        this.backTravelTime
    )

val Driver.FIO: String  // Экстеншн для выделения фамилии с инициалами из объекта машиниста
    get() = StringBuilder()
        .append(this.surname)
        .append(if (this.name.isNotBlank()) " ${this.name.first()}." else "")
        .append(if (this.patronymic.isNotBlank()) " ${this.patronymic.first()}." else "")
        .toString()

fun TrainRun.changeDay(dayNumber: Int): TrainRun {
    val time = this.startTime
    return TrainRun(
        0,
        this.trainId,
        this.trainNumber,
        this.trainDirection,
        this.trainPeriodicity,
        this.driverId,
        this.driverName,
        LocalDateTime.of(time.year, time.month.value, dayNumber, time.hour, time.minute),
        this.travelTime,
        this.travelRestTime,
        this.backTravelTime
    )
}