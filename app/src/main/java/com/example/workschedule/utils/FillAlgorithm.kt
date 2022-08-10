package com.example.workschedule.utils

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.restHours
import com.example.workschedule.domain.secondNightWorkBan
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

/*
 * Метод для выборки из списка выезда поездов машинистов (в определенное время) и возврат списка Id тех машинистов,
 * которые были прошлую ночь в рейсе и попадают в ночь в этом выезде. Т.е. списка тех кого нельзя ставить на новый выезд в
 * определенное время.
 */
fun getDriversIdsWhoWorkSecondNight(
    trainRunList: List<TrainRun>, currentTrainRun: TrainRun, drivers: List<Driver>
): List<Int> {
    fun periodInNight(
        searchDay: LocalDateTime, startTime: LocalDateTime, endTime: LocalDateTime
    ): Boolean {
        val nightStart = searchDay.toLocalDate().atStartOfDay()
        val nightEnd = nightStart.plusHours(6)
        return startTime <= nightEnd && endTime >= nightStart
    }

    val lastNightWorkDriversIdList = trainRunList
        .asSequence()
        .filter { it.driverId > 0 } // Отсеиваем записи в которых машинисты еще не назначены
        .filter {
            currentTrainRun.startTime.minusDays(1).toLocalDate().atStartOfDay() > it.startTime.plus(
                it.travelTime + it.travelRestTime + it.backTravelTime,
                ChronoUnit.MILLIS
            )
        }   // Отсеиваем тех у кого смена закончилась до вчерашней ночи
        .filter {
            periodInNight(
                currentTrainRun.startTime.minusDays(1),
                it.startTime,
                it.startTime.plus(
                    it.travelTime, ChronoUnit.MILLIS
                )
            ) || periodInNight(
                currentTrainRun.startTime.minusDays(1),
                it.startTime.plus(
                    it.travelTime + it.travelRestTime, ChronoUnit.MILLIS
                ),
                it.startTime.plus(
                    it.travelTime + it.travelRestTime + it.backTravelTime, ChronoUnit.MILLIS
                )
            )
        }   // Выбираем тех, кто работал в прошлую ночь
        .map { it.driverId }    // Мапим в список их Id
        .toList()
    val startTime = currentTrainRun.startTime
    val currentTrainRunInNight =
        periodInNight(
            startTime, startTime, startTime.plus(
                currentTrainRun.travelTime, ChronoUnit.MILLIS
            )
        ) || periodInNight(
            startTime, startTime.plus(
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

fun List<Driver>.getFreeDriversForTrainRun(trainRunList: List<TrainRun>, trainRun: TrainRun) =
    this // Берём список машинистов
        // Отсеиваем тех, кто не имеет доступа к этому поезду
        .filter { trainRun.trainId in it.accessTrainsId }
        // Отсеиваем тех кто занят или на отдыхе после выезда
        .filter { it.id !in getBusyOrRestDriversIdsOnTime(trainRunList, trainRun.startTime) }

/*
 * Метод для заполнения списка выезда поездов машинистами из списка машинистов по алгоритму
 */
fun List<TrainRun>.fillTrainRunListWithDrivers(drivers: List<Driver>): List<TrainRun> {
    this.forEach { trainRun ->  // Для каждого выезда поезда
        if (trainRun.driverId == 0) {   // Если машинист не назначен
            trainRun.driverId = drivers
                // Берем список свободных машинистов на данное направление
                .getFreeDriversForTrainRun(this, trainRun)
                .filter {
                    if (secondNightWorkBan) {
                        it.id !in getDriversIdsWhoWorkSecondNight(this, trainRun, drivers)
                    } else {
                        true
                    }
                } // Отсеиваем по условию работы двух ночей подряд
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
            }
        }
    }
    return this
}
