package com.example.workschedule.utils

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.restHours
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/*
 * Метод для выборки из списка выезда поездов машинистов (в определенное время) и возврат списка Id тех машинистов,
 * которые в рейсе или на отдыхе после рейса. Т.е. списка тех кого нельзя ставить на новый выезд в
 * определенное время.
 */
fun getBusyOrRestDriversIdsOnTime(trainRunList: List<TrainRun>, time: LocalDateTime) = trainRunList
    .filter { it.driverId > 0 } // Отсеиваем записи в которых машинисты еще не назначены
    .filter {
        time >= it.startTime && time <= it.startTime.plus(
            it.travelTime + it.travelRestTime + it.backTravelTime + restHours.hoursToMillis,
            ChronoUnit.MILLIS
        )
    }   // Выбираем тех, кто в рейсе или на отдыхе после рейса
    .map { it.driverId }    // Мапим в список их Id

fun Driver.noThirdNight(trainRunList: List<TrainRun>, trainRun: TrainRun): Boolean {
    var countNight = 0
//    если явка туда - ночь
    if (trainRun.startTime.isNightPeriod()) {
        countNight++
    }
//    если сдача туда - ночь, а явка и сдача принадлежат к разным суткам
    if (trainRun.startTime.plus(
            trainRun.travelTime, ChronoUnit.MILLIS
        ).isNightPeriod() && (trainRun.startTime.toLocalDate()
            .atStartOfDay() != trainRun.startTime.plus(
            trainRun.travelTime, ChronoUnit.MILLIS
        ).toLocalDate().atStartOfDay()
                )
    ) {
        countNight++
    }
//    если явка обратно - ночь
    if (trainRun.startTime.plus(
            trainRun.travelTime + trainRun.travelRestTime,
            ChronoUnit.MILLIS
        )
            .isNightPeriod()
    ) {
        countNight++
    }
//    если сдача обратно - ночь, а явка обратно и сдача обратно принадлежат к разным суткам
    if (trainRun.startTime.plus(
            trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
            ChronoUnit.MILLIS
        ).isNightPeriod() && (trainRun.startTime.plus(
            trainRun.travelTime + trainRun.travelRestTime,
            ChronoUnit.MILLIS
        ).toLocalDate()
            .atStartOfDay() != trainRun.startTime.plus(
            trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
            ChronoUnit.MILLIS
        ).toLocalDate().atStartOfDay()
                )
    ) {
        countNight++
    }


    return countNight < 3
}


/*
 ТЕКУЩАЯ РАБОТА!!!
 */
//    проверяет, является ли время ночным (00:00 - 06:00)
fun LocalDateTime.isNightPeriod(): Boolean {
    val nightStart = this.toLocalDate().atStartOfDay()
    val nightEnd = nightStart.plusHours(endNightPeriodHours.toLong())
    return ((this <= nightEnd && this >= nightStart))
}

fun getDriversIdsWhoWorkSecondNight(
    trainRunList: List<TrainRun>, currentTrainRun: TrainRun, drivers: List<Driver>
): List<Int> {
    val lastNightWorkDriversIdList = trainRunList
        .asSequence()
//            выбираем заполненные машинистами поездки
        .filter { it.driverId > 0 }
        .filter {
            currentTrainRun.startTime.minusDays(1).toLocalDate().atStartOfDay() > it.startTime.plus(
                it.travelTime + it.travelRestTime + it.backTravelTime,
                ChronoUnit.MILLIS
            )
        }
//            Проверка на 2 ночи
        .filter {
            currentTrainRun.startTime.minusDays(1).isNightPeriod(
//                it.startTime,
                it.startTime.plus(
                    it.travelTime, ChronoUnit.MILLIS
                )
            ) || currentTrainRun.startTime.minusDays(1).isNightPeriod(
                it.startTime.plus(
                    it.travelTime + it.travelRestTime, ChronoUnit.MILLIS
                ),
                it.startTime.plus(
                    it.travelTime + it.travelRestTime + it.backTravelTime, ChronoUnit.MILLIS
                )
            )
        }
        .map { it.driverId }    // Мапим в список их Id
        .toList()
    val startTime = currentTrainRun.startTime
//    является ли текущая поездка ночной (не доделано)
    val currentTrainRunInNight =

        startTime.isNightPeriod(
            startTime, startTime.plus(
                currentTrainRun.travelTime, ChronoUnit.MILLIS
            )
        ) || startTime.isNightPeriod(
            startTime.plus(
                currentTrainRun.travelTime + currentTrainRun.travelRestTime, ChronoUnit.MILLIS
            ),
            startTime.plus(
                currentTrainRun.travelTime + currentTrainRun.travelRestTime + currentTrainRun.backTravelTime,
                ChronoUnit.MILLIS
            )
        )
    return if (!currentTrainRunInNight) {
        listOf()
    } else {
        drivers.getFreeDriversForTrainRun(
            trainRunList, currentTrainRun
        ) // Берем список свободных машинистов на данное направление
            .map { it.id } // Мапим их Id в список
            .filter { it in lastNightWorkDriversIdList } // Оставляем тех, кто работал прошлой ночью
    }
}

fun List<TrainRun>.fillTrainRunListWithDriversMy(drivers: List<Driver>): List<TrainRun> {
    this.forEach { trainRun ->  // Для каждого выезда поезда
        if (trainRun.driverId == 0) {   // Если машинист не назначен
            trainRun.driverId = drivers
                // Берем список свободных машинистов на данное направление
                .getFreeDriversForTrainRun(this, trainRun)
//                .filter {
//                    if (secondNightWorkBan) {
//                        it.id !in getDriversIdsWhoWorkSecondNight(this, trainRun, drivers)
//                    } else {
//                        true
//                    }
//                }
                // Отсеиваем по условию работы двух ночей подряд
                // Из оставшихся выбираем того машиниста, у которого меньше всего отработано часов
                .minByOrNull { it.totalTime }?.id ?: 0
            if (trainRun.driverId != 0) { // Если машинист найден
                drivers.find { it.id == trainRun.driverId }?.let { driver ->
                    trainRun.driverName = driver.FIO
                    // Рассчитываем рабочее время в поездке
                    val workTime = trainRun.travelTime + trainRun.backTravelTime
                    driver.totalTime = driver.totalTime.plus(workTime)
                    val travelEndTime = trainRun.startTime.plus(
                        trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
                        ChronoUnit.MILLIS
                    )
                    if (LocalDateTime.now() > travelEndTime) {
                        driver.workedTime = driver.workedTime.plus(workTime)
                    }
                } //  то заполняем поля выезда и
            } else {
                // Вернуть сообщение, что не хватает машинистов для закрытия всех выездов
//                Toast.makeText(MainActivity(), "Недостаточно машинистов", Toast.LENGTH_SHORT).show()
            }
        }
    }
    return this
}

fun List<Driver>.getFreeDriversForTrainRun(trainRunList: List<TrainRun>, trainRun: TrainRun) =
    this // Берём список машинистов
        // Отсеиваем тех, кто не имеет допуска к этому направлению
        .filter { trainRun.trainId in it.accessTrainsId }
        // Отсеиваем тех кто занят или на отдыхе после поездки
        .filter { it.id !in getBusyOrRestDriversIdsOnTime(trainRunList, trainRun.startTime) }
//            отсеиваем тех, для кого текущая поездка будет с 3-й ночью
        .filter { it.noThirdNight(trainRunList, trainRun) }

/*
 * Метод для заполнения списка выезда поездов машинистами из списка машинистов по алгоритму
 */
fun List<TrainRun>.fillTrainRunListWithDrivers(drivers: List<Driver>): List<TrainRun> {
    this.forEach { trainRun ->  // Для каждого выезда поезда
        if (trainRun.driverId == 0) {   // Если машинист не назначен
            trainRun.driverId = drivers
                // Берем список свободных машинистов на данное направление
                .getFreeDriversForTrainRun(this, trainRun)
//                .filter {
//                    if (secondNightWorkBan) {
//                        it.id !in getDriversIdsWhoWorkSecondNight(this, trainRun, drivers)
//                    } else {
//                        true
//                    }
//                }
                // Отсеиваем по условию работы двух ночей подряд
                // Из оставшихся выбираем того машиниста, у которого меньше всего отработано часов
                .minByOrNull { it.totalTime }?.id ?: 0
            if (trainRun.driverId != 0) { // Если машинист найден
                drivers.find { it.id == trainRun.driverId }?.let { driver ->
                    trainRun.driverName = driver.FIO
                    // Рассчитываем рабочее время в поездке
                    val workTime = trainRun.travelTime + trainRun.backTravelTime
                    driver.totalTime = driver.totalTime.plus(workTime)
                    val travelEndTime = trainRun.startTime.plus(
                        trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
                        ChronoUnit.MILLIS
                    )
                    if (LocalDateTime.now() > travelEndTime) {
                        driver.workedTime = driver.workedTime.plus(workTime)
                    }
                } //  то заполняем поля выезда и
            } else {
                // Вернуть сообщение, что не хватает машинистов для закрытия всех выездов
//                Toast.makeText(MainActivity(), "Недостаточно машинистов", Toast.LENGTH_SHORT).show()
            }
        }
    }
    return this
}

