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
fun getBusyOrRestDriversIdsOnTime(trainRunList: List<TrainRun>, time: LocalDateTime): List<Int> {
    return trainRunList
        .filter { it.driverId > 0 } // Отсеиваем записи в которых машинисты еще не назначены
        .filter {
            time >= it.startTime && time <= it.startTime.plus(
                it.travelTime + it.travelRestTime + it.backTravelTime + restHours.hoursToMillis,
                ChronoUnit.MILLIS
            )
        }   // Выбираем тех, кто в рейсе или на отдыхе после рейса
        .map { it.driverId }    // Мапим в список их Id
}

/**
 * Метод для заполнения списка выезда поездов машинистами из списка машинистов по алгоритму
 */
fun List<TrainRun>.fillTrainRunListWithDrivers(drivers: List<Driver>): List<TrainRun> {
    this.forEach { trainRun ->  // Для каждого выезда поезда
        if (trainRun.driverId == 0) {   // Если машинист не назначен
            trainRun.driverId = drivers // Берём список машинистов
                // Отсеиваем тех кто занят или на отдыхе
                .filter {
                    it.id !in getBusyOrRestDriversIdsOnTime(
                        this,
                        trainRun.startTime
                    )
                }
                // Отсеиваем тех, кто не имеет доступа к этому поезду
                .filter { trainRun.trainId in it.accessTrainsId }
                // Из оставшихся выбираем того машиниста, у которого меньше всего отработано часов
                .minByOrNull { it.totalTime }?.id ?: 0
            if (trainRun.driverId != 0) { //  то заполняем поля выезда
                with(drivers.find { it.id == trainRun.driverId }) {
                    this?.let {
                        trainRun.driverName = it.FIO
                    }
                }
                // Рассчитываем рабочее время в поездке
                val workTime = drivers.find { it.id == trainRun.driverId }?.totalTime?.plus(
                    trainRun.travelTime + trainRun.backTravelTime
                )
                // Если расчет успешный прибавляем время поездки к рабочему времени машиниста
                if (workTime != null) {
                    drivers.find { it.id == trainRun.driverId }?.totalTime = workTime
                }
            } else {
                // Вернуть сообщение, что не хватает машинистов для закрытия всех выездов
            }
        }
    }
    return this
}
