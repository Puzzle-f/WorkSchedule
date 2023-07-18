package com.example.workschedule.utils

import com.example.workschedule.data.database.direction.DirectionEntity
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.permission.PermissionEntity
import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.data.database.trainrun.TrainRunEntity
import com.example.workschedule.data.database.weekend.WeekendEntity
import com.example.workschedule.domain.models.*
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
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

val List<DirectionEntity>.fromDTOListTrain: List<Direction> // Экстеншн преобразования списка TrainEntity в Train
    get() = this.map { Direction(it.id, it.direction) }

val DirectionEntity.fromDTO: Direction // Экстеншн преобразования TrainEntity в Train
    get() = Direction(this.id, this.direction)

val Direction.toDTO: DirectionEntity // Экстеншн преобразования Train в TrainEntity
    get() = DirectionEntity(this.id, this.name)

val List<DriverEntity>.fromDTOListDriver: List<Driver> // Экстеншн преобразования списка DriverEntity в Driver
    get() = this.map {
        Driver(
            it.id,
            it.personalNumber,
            it.surname,
            it.name,
            it.patronymic
        )
    }

val List<Driver>.toDTOListDriver: List<DriverEntity> // Экстеншн преобразования списка Driver в DriverEntity
    get() = this.map {
        DriverEntity(
            it.id,
            it.personalNumber,
            it.surname,
            it.name,
            it.patronymic
        )
    }

val DriverEntity.fromDTO: Driver // Экстеншн преобразования DriverEntity в Driver
    get() = Driver(
        this.id,
        this.personalNumber,
        this.surname,
        this.name,
        this.patronymic
    )

val Driver.toDTO: DriverEntity // Экстеншн преобразования Driver в DriverEntity
    get() = DriverEntity(
        this.id,
        this.personalNumber,
        this.surname,
        this.name,
        this.patronymic
    )

val List<TrainRunEntity>.fromDTOListTrainRun: List<TrainRun> // Экстеншн преобразования списка TrainRunEntity в TrainRun
    get() = this.map {
        TrainRun(
            it.id,
            it.number,
            it.driverId,
            it.direction,
            it.startTime,
            it.endTime,
            it.countNight,
            it.workTime,
            it.periodicity.toPeriodicity,
            it.isEditManually,
            it.note
        )
    }

val TrainRunEntity?.fromDTO: TrainRun? // Экстеншн преобразования TrainRunEntity в TrainRun
    get() = if(this == null) null
            else
        TrainRun(
        this.id,
        this.number,
        this.driverId,
        this.direction,
        this.startTime,
        this.endTime,
        this.countNight,
        this.workTime,
        this.periodicity.toPeriodicity,
        this.isEditManually,
        this.note
    )

val TrainRun.toDTO: TrainRunEntity // Экстеншн преобразования TrainRun в TrainRunEntity
    get() = TrainRunEntity(
        this.id,
        this.number,
        this.driverId,
        this.direction,
        this.startTime,
        this.travelTime,
        this.countNight,
        this.workTime,
        this.periodicity.toInt,
        this.isEditManually,
        this.note
    )

val Driver.FIO: String  // Экстеншн для выделения фамилии с инициалами из объекта машиниста
    get() = StringBuilder()
        .append(this.surname)
        .append(if (this.name.isNotBlank()) " ${this.name.first()}." else "")
        .append(if (this.patronymic.isNotBlank()) " ${this.patronymic.first()}." else "")
        .toString()

val Permission.toDTO: PermissionEntity
    get() = PermissionEntity(
        this.idDriver,
        this.idDirection
    )

val PermissionEntity.fromDto: Permission
    get() = Permission(
        this.idDriver,
        this.idDirection
    )

val Status.toDTO: StatusEntity
get() = StatusEntity(
    this.idDriver,
    this.date,
    this.status,
    this.countNight,
    this.workedTime,
    this.idBlock
)

val StatusEntity.fromDTO: Status
get() = Status(
    this.idDriver,
    this.date,
    this.status,
    this.countNight,
    this.workedTime,
    this.idBlock
)

val List<StatusEntity>.fromDTO: List<Status>
get() = this.map {
    Status(
        it.idDriver,
        it.date,
        it.status,
        it.countNight,
        it.workedTime,
        it.idBlock
    )
}


val Weekend.toEntity: WeekendEntity
    get() = WeekendEntity(
        this.driverId,
        this.date
    )

val WeekendEntity.toWeekend: Weekend
    get() = Weekend(
        this.driverId,
        this.date
    )

fun TrainRun.changeDay(dayNumber: Int): TrainRun {
    val time = this.startTime.toLocalDateTime()
    return TrainRun(
        0,
        this.number,
        this.driverId,
        this.direction,
        startTime = LocalDateTime.of(time.year, time.month.value, dayNumber, time.hour, time.minute)
            .toLong(),
        this.travelTime,
        this.countNight,
        this.workTime,
        this.periodicity,
        this.isEditManually,
        this.note
    )
}

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        TimeZone.getDefault().toZoneId()
    )

fun LocalDateTime.toLong() =
    this.atZone(TimeZone.getDefault().toZoneId())
        .toInstant().toEpochMilli()