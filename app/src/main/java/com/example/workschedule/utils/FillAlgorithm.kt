package com.example.workschedule.utils

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.restHours
import java.time.LocalDateTime
import java.time.Period
import java.time.temporal.ChronoUnit

/*
 * Метод для выборки из списка выезда поездов машинистов (в определенное время) и возврат списка Id тех машинистов,
 * которые в рейсе или на отдыхе после рейса. Т.е. списка тех кого нельзя ставить на новый выезд в
 * определенное время.
 */
fun getBusyOrRestDriversIdsOnTime(trainRunList: List<TrainRun>, time: LocalDateTime) = trainRunList
//    .filter { it.driverId > 0 } // Отсеиваем записи в которых машинисты еще не назначены
//    .filter {
//        time >= it.startTime.toLocalDateTime() && time <= it.startTime.toLocalDateTime().plus(
//            it.travelTime + it.travelRestTime + it.backTravelTime + restHours.hoursToMillis,
//            ChronoUnit.MILLIS
//        )
//    }   // Выбираем тех, кто в рейсе или на отдыхе после рейса
//    .map { it.driverId }    // Мапим в список их Id

//fun Driver.noThirdNight(
///* результат запроса getTrainRunByDriverIdAfterDate*/trainRunList: List<TrainRun>,
///* поездка, в которую планируем поставить машиниста*/ trainRun: TrainRun
//): Boolean {
//
//}


/*
 ТЕКУЩАЯ РАБОТА!!!
 */
//    проверяет, является ли время ночным (00:00 - 06:00)
fun LocalDateTime.isNightPeriod(): Boolean {
    val nightStart = this.toLocalDate().atStartOfDay()
    val nightEnd = nightStart.plusHours(endNightPeriodHours.toLong())
    return ((this <= nightEnd && this >= nightStart))
}

//fun TrainRun.halfwayForTurnout(start: LocalDateTime): LocalDateTime {
//    val start = this.startTime.toLocalTime()
//    val end = this.startTime.plusNanos(this.travelTime / 2)
//
//}


//fun getDriversIdsWhoWorkSecondNight(
//    trainRunList: List<TrainRun>, currentTrainRun: TrainRun, drivers: List<Driver>
//): List<Int> {
//    val lastNightWorkDriversIdList = trainRunList
//        .asSequence()
////            выбираем заполненные машинистами поездки
//        .filter { it.driverId > 0 }
//        .filter {
//            currentTrainRun.startTime.minusDays(1).toLocalDate().atStartOfDay() > it.startTime.plus(
//                it.travelTime + it.travelRestTime + it.backTravelTime,
//                ChronoUnit.MILLIS
//            )
//        }
////            Проверка на 2 ночи
//        .filter {
//            currentTrainRun.startTime.minusDays(1).isNightPeriod(
////                it.startTime,
//                it.startTime.plus(
//                    it.travelTime, ChronoUnit.MILLIS
//                )
//            ) || currentTrainRun.startTime.minusDays(1).isNightPeriod(
//                it.startTime.plus(
//                    it.travelTime + it.travelRestTime, ChronoUnit.MILLIS
//                ),
//                it.startTime.plus(
//                    it.travelTime + it.travelRestTime + it.backTravelTime, ChronoUnit.MILLIS
//                )
//            )
//        }
//        .map { it.driverId }    // Мапим в список их Id
//        .toList()
//    val startTime = currentTrainRun.startTime
////    является ли текущая поездка ночной (не доделано)
//    val currentTrainRunInNight =
//
//        startTime.isNightPeriod(
//            startTime, startTime.plus(
//                currentTrainRun.travelTime, ChronoUnit.MILLIS
//            )
//        ) || startTime.isNightPeriod(
//            startTime.plus(
//                currentTrainRun.travelTime + currentTrainRun.travelRestTime, ChronoUnit.MILLIS
//            ),
//            startTime.plus(
//                currentTrainRun.travelTime + currentTrainRun.travelRestTime + currentTrainRun.backTravelTime,
//                ChronoUnit.MILLIS
//            )
//        )
//    return if (!currentTrainRunInNight) {
//        listOf()
//    } else {
//        drivers.getFreeDriversForTrainRun(
//            trainRunList, currentTrainRun
//        ) // Берем список свободных машинистов на данное направление
//            .map { it.id } // Мапим их Id в список
//            .filter { it in lastNightWorkDriversIdList } // Оставляем тех, кто работал прошлой ночью
//    }
//}

//fun List<TrainRun>.fillAlgorithm(drivers: List<Driver>): List<TrainRun>{
//    this.forEach { trainRun ->
//        if (trainRun.driverId == 0){
//            val driverResult = drivers.filter {
//                driver -> driver
//            }
//
//
//        }
//
//
//
//    }
//
//
//}

//fun List<TrainRun>.fillTrainRunListWithDriversMy(drivers: List<Driver>): List<TrainRun> {
//    this.forEach { trainRun ->  // Для каждого выезда поезда
//        if (trainRun.driverId == 0) {   // Если машинист не назначен
//            trainRun.driverId = drivers
//                // Берем список свободных машинистов на данное направление
//                .getFreeDriversForTrainRun(this, trainRun)
//                .filter {
//                    if (secondNightWorkBan) {
//                        it.id !in getDriversIdsWhoWorkSecondNight(this, trainRun, drivers)
//                    } else {
//                        true
//                    }
//                }
//                // Отсеиваем по условию работы двух ночей подряд
//                // Из оставшихся выбираем того машиниста, у которого меньше всего отработано часов
//                .minByOrNull { it.totalTime }?.id ?: 0
//            if (trainRun.driverId != 0) { // Если машинист найден
//                drivers.find { it.id == trainRun.driverId }?.let { driver ->
//                    trainRun.driverName = driver.FIO
//                    // Рассчитываем рабочее время в поездке
//                    val workTime = trainRun.travelTime + trainRun.backTravelTime
//                    driver.totalTime = driver.totalTime.plus(workTime)
//                    val travelEndTime = trainRun.startTime.toLocalDateTime().plus(
//                        trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
//                        ChronoUnit.MILLIS
//                    )
//                    if (LocalDateTime.now() > travelEndTime) {
//                        driver.workedTime = driver.workedTime.plus(workTime)
//                    }
//                } //  то заполняем поля выезда и
//            } else {
//                // Вернуть сообщение, что не хватает машинистов для закрытия всех выездов
////                Toast.makeText(MainActivity(), "Недостаточно машинистов", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    return this
//}


/*
 * Метод для заполнения списка выезда поездов машинистами из списка машинистов по алгоритму
 */


