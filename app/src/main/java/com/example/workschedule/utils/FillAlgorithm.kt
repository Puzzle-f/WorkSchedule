package com.example.workschedule.utils

import com.example.workschedule.domain.models.DomainDriverModel
import com.example.workschedule.domain.models.DomainTrainModel
import com.example.workschedule.domain.models.DomainTrainRunModel
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit
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
        return String.format("%2d:%02d", hours, minutes)
    }

// todo ↓↓↓ Хардкод, после настройки приложения удалить ↓↓↓

const val restHours = 16  // Константа опряделяющая количество часов отдыха после работы

val trainRunList = listOf(
    DomainTrainRunModel(
        1, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
        8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        2, 14, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 12, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        3, 92, 0,
        LocalDateTime.of(2022, Month.APRIL, 1, 16, 30),
        5.hoursToMillis, 5.hoursToMillis, 5.hoursToMillis
    ),
    DomainTrainRunModel(
        4, 32, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 3, 30),
        6.hoursToMillis, 8.hoursToMillis, 6.hoursToMillis
    ),
    DomainTrainRunModel(
        5, 51, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 6, 30),
        7.hoursToMillis, 4.hoursToMillis, 6.hoursToMillis
    ),
    DomainTrainRunModel(
        6, 96, 0,
        LocalDateTime.of(2022, Month.APRIL, 2, 22, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        7, 72, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 30),
        7.hoursToMillis, 4.hoursToMillis, 7.hoursToMillis
    ),
    DomainTrainRunModel(
        8, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 9.hoursToMillis
    ),
    DomainTrainRunModel(
        9, 99, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    DomainTrainRunModel(
        10, 80, 0,
        LocalDateTime.of(2022, Month.APRIL, 3, 20, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        11, 103, 0,
        LocalDateTime.of(2022, Month.APRIL, 4, 6, 30),
        8.hoursToMillis, 6.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        12, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 5, 6, 30),
        15.hoursToMillis, 4.hoursToMillis, 15.hoursToMillis
    ),
    DomainTrainRunModel(
        13, 11, 0,
        LocalDateTime.of(2022, Month.APRIL, 5, 3, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        14, 125, 0,
        LocalDateTime.of(2022, Month.APRIL, 6, 13, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        15, 51, 0,
        LocalDateTime.of(2022, Month.APRIL, 7, 12, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        16, 14, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 14, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        17, 72, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 10, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        18, 99, 0,
        LocalDateTime.of(2022, Month.APRIL, 8, 16, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        19, 32, 0,
        LocalDateTime.of(2022, Month.APRIL, 9, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    DomainTrainRunModel(
        20, 120, 0,
        LocalDateTime.of(2022, Month.APRIL, 9, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    )
)

val driverList = listOf(
    DomainDriverModel(1, "Иван", "", "", 0, 0, listOf(120, 92, 14)),
    DomainDriverModel(2, "Олег", "", "", 0, 0, listOf(32, 14, 51)),
    DomainDriverModel(3, "Василий", "", "", 0, 0, listOf(51, 72, 80)),
    DomainDriverModel(4, "Николай", "", "", 0, 0, listOf(96, 72)),
    DomainDriverModel(5, "Семен", "", "", 0, 0, listOf(90, 99)),
    DomainDriverModel(6, "Иннокентий", "", "", 0, 0, listOf(103, 11, 125, 96)),
    DomainDriverModel(7, "Дмитрий", "", "", 0, 0, listOf(72, 51, 92)),
    DomainDriverModel(8, "Вячеслав", "", "", 0, 0, listOf(92, 96)),
    DomainDriverModel(9, "Александр", "", "", 0, 0, listOf(120, 92)),
    DomainDriverModel(10, "Андрей", "", "", 0, 0, listOf(72, 32, 11)),
    DomainDriverModel(11, "Владимир", "", "", 0, 0, listOf(103, 14, 99)),
    DomainDriverModel(12, "Павел", "", "", 0, 0, listOf(125, 14)),
//    Driver(13,"Леонид", "", "", 0, 0, listOf(11, 99, 32)),
//    Driver(14,"Алексей", "", "", 0, 0, listOf(14, 80, 11)),
//    Driver(15,"Юрий", "", "", 0, 0, listOf(99, 92, 96)),
//    Driver(16,"Виталий", "", "", 0, 0, listOf(32, 96, 11)),
//    Driver(17,"Всеволод", "", "", 0, 0, listOf(120, 92)),
//    Driver(18,"Никита", "", "", 0, 0, listOf(103, 14)),
//    Driver(19,"Виктор", "", "", 0, 0, listOf(72, 99)),
//    Driver(20,"Евлампий", "", "", 0, 0, listOf(32, 92, 72))
)

val trainList = listOf(
    DomainTrainModel(120, "Москва"),
    DomainTrainModel(92, "Санкт-Петербург"),
    DomainTrainModel(32, "Краснодар"),
    DomainTrainModel(14, "Ростов-на-Дону"),
    DomainTrainModel(51, "Воронеж"),
    DomainTrainModel(96, "Туапсе"),
    DomainTrainModel(72, "Ставрополь"),
    DomainTrainModel(80, "Тюмень"),
    DomainTrainModel(99, "Нижний Новгород"),
    DomainTrainModel(103, "Новороссийск"),
    DomainTrainModel(11, "Казань"),
    DomainTrainModel(125, "Киров")
)

// todo ↑↑↑ Хардкод, после настройки приложения удалить ↑↑↑

/**
 * Метод для выборки из списка выезда поездов машинистов (в определенное время) и возврат списка Id тех машинистов,
 * которые в рейсе или на отдыхе после рейса. Т.е. списка тех кого нельзя ставить на новый выезд в
 * определенное время.
 */
fun getBusyOrRestDriversIdsOnTime(trainRunList: List<DomainTrainRunModel>, time: LocalDateTime): List<Int> {
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
fun List<DomainTrainRunModel>.fillTrainRunListWithDrivers(drivers: List<DomainDriverModel>) {
    this.forEach { trainRun ->  // Для каждого выезда поезда
        if (trainRun.driverId == 0) {   // Если машинист не назначен
            trainRun.driverId = drivers // Берём список машинистов
                // Отсеиваем тех кто занят или на отдыхе
                .filter {
                    it.id !in getBusyOrRestDriversIdsOnTime(
                        trainRunList,
                        trainRun.startTime
                    )
                }
                // Отсеиваем тех, кто не имеет доступа к этому поезду
                .filter { it.accessTrainsId.contains(trainRun.trainNumber) }
                // Из оставшихся выбираем того машиниста, у которого меньше всего отработано часов
                .minByOrNull { it.totalTime }?.id ?: 0
            // Рассчитываем рабочее время в поездке
            val workTime = drivers.find { it.id == trainRun.driverId }?.totalTime?.plus(
                trainRun.travelTime + trainRun.backTravelTime
            )
            // Если расчет успешный, прибавляем время поездки к рабочему времени машиниста
            if (workTime != null) {
                drivers.find { it.id == trainRun.driverId }?.totalTime = workTime
            }
        }
    }
}
