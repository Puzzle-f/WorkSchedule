package com.example.workschedule.domain

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import com.example.workschedule.utils.toDTO
import java.time.LocalDateTime
import java.time.Month

// todo ↓↓↓ Хардкод, после настройки приложения удалить ↓↓↓

const val restHours = 16  // Константа опряделяющая количество часов отдыха после работы
const val secondNightWorkBan = true // Константа условия запрета двух ночей подряд

val trainList = listOf(
    Train(1, "Лиски"),
    Train(2, "Россошь"),
    Train(3, "Таганрог"),
    Train(4, "Кисловодск"),
    Train(5, "Адлер"),
    Train(6, "9 км")
)

val driverList = listOf(
    Driver(0, 15, "Иванов", "Иван", "Иванович", 5, 10, listOf(1, 2, 3)),
    Driver(0, 17, "Петров", "Олег", "Дмитриевич", 3, 2, listOf(1, 2, 3)),
    Driver(0, 12, "Запойный", "Василий", "Филиппов", 100, 2, listOf(1, 2, 3)),
    Driver(0, 11, "Тимченко", "Николай", "Петрович", 0, 0, listOf(1, 2, 3)),
    Driver(0, 1, "Слепаков", "Семен", "Владимирович", 10, 1, listOf(1, 2, 3)),
    Driver(0, 18, "Пронин", "Иннокентий", "Юрьевич", 13, 2, listOf(4, 5, 6)),
    Driver(0, 21, "Гуськов", "Дмитрий", "Зурабович", 7, 3, listOf(4, 5, 6)),
    Driver(0, 31, "Зайцев", "Вячеслав", "Зиновьевич", 16, 1, listOf(4, 5, 6)),
    Driver(0, 61, "Сотников", "Александр", "Александрович", 8, 9, listOf(4, 5, 6)),
    Driver(0, 23, "Пронин", "Андрей", "Юрьевич", 8, 4, listOf(4, 5, 6)),
    Driver(0, 25, "Ясный", "Владимир", "Егорович", 15, 2, listOf(4, 5, 6)),
    Driver(0, 36, "Причудный", "Павел", "Владимирович", 7, 0, listOf(1, 3, 5)),
    Driver(0, 51, "Свистунов", "Леонид", "Геннадиевич", 5, 6, listOf(1, 3, 5)),
    Driver(0, 55, "Балабанов", "Алексей", "Михайлович", 0, 19, listOf(1, 3, 5)),
    Driver(0, 40, "Дудь", "Юрий", "Семёнович", 0, 54, listOf(1, 3, 5)),
    Driver(0, 30, "Кличко", "Виталий", "Леонидович", 21, 0, listOf(1, 3, 5)),
    Driver(0, 22, "Чаплин", "Всеволод", "Кириллович", 3, 0, listOf(2, 4, 6)),
    Driver(0, 29, "Бесстрашная", "Никита", "Харвиевна", 85, 6, listOf(2, 4, 6)),
    Driver(0, 39, "Пелевин", "Виктор", "Алексеевич", 65, 1, listOf(2, 4, 6)),
    Driver(0, 77, "Романов", "Евлампий", "Булатович", 8, 2, listOf(2, 4, 6))
)

val trainRunList = listOf(
    TrainRun(
        1, 1, 120, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
        8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        2, 4, 48, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 12, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        3, 2, 92, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 16, 30),
        5.hoursToMillis, 5.hoursToMillis, 5.hoursToMillis
    ),
    TrainRun(
        4, 3, 32, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 3, 30),
        6.hoursToMillis, 8.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        5, 5, 51, "9 км", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 6, 30),
        7.hoursToMillis, 4.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        6, 6, 96, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 22, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        7, 7, 72, "Адлер", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 30),
        7.hoursToMillis, 4.hoursToMillis, 7.hoursToMillis
    ),
    TrainRun(
        8, 1, 120, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 9.hoursToMillis
    ),
    TrainRun(
        9, 2, 125, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        10, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 4, 9, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        11, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 4, 7, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        12, 1, 103, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 4, 18, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        13, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 4, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        14, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 5, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        15, 5, 102, "Адлер", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 5, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        16, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 5, 12, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        17, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 5, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        18, 6, 382, "9 км", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 5, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        19, 6, 382, "9 км", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 6, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        20, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 6, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        21, 1, 92, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 6, 18, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        22, 1, 3, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 6, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        23, 1, 103, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        24, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        25, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 11, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        26, 5, 102, "Адлер", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 18, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        27, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        28, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 7, 22, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        29, 2, 125, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        30, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        31, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 12, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        32, 1, 3, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 18, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        33, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        34, 6, 33, "9 км", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 8, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        35, 2, 125, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 9, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        36, 5, 102, "Адлер", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 9, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        37, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 9, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        38, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 9, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        39, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 9, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        40, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 10, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        41, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 10, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        42, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 10, 12, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        43, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 10, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        44, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 11, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        45, 3, 33, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 11, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        46, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 11, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        47, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 12, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        48, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 12, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        49, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 12, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        50, 6, 101, "9 км", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 12, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        51, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 13, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        52, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 13, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        53, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 13, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        54, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 13, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        55, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 14, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        56, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 14, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        57, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 14, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        58, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 15, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        59, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 15, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        60, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 16, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        61, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 16, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        62, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 16, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        63, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 16, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        64, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 17, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        65, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 17, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        66, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 17, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        67, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 17, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        68, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 18, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        69, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 18, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        70, 2, 61, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 18, 14, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        71, 1, 101, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 18, 15, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        72, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 18, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ), TrainRun(
        22, 1, 3, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 19, 20, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        23, 1, 103, "Лиски", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 19, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        24, 2, 33, "Россошь", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 19, 10, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    ),
    TrainRun(
        25, 3, 120, "Таганрог", TrainPeriodicity.SINGLE, 0, "",
        LocalDateTime.of(2022, Month.APRIL, 19, 11, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    )


)

// Метод записи хард-кода в Базу Данных для демонстрации
suspend fun saveFakeDataToDB(database: ScheduleDataBase) {
    trainList.forEach { database.trainDao().saveTrain(it.toDTO) }
    driverList.forEach { database.driverDao().saveDriver(it.toDTO) }
    trainRunList.forEach { database.trainRunDao().saveTrainRun(it.toDTO) }
}

// Метод очистки базы данных с очисткой ключей автоинкремента
fun clearDatabase(database: ScheduleDataBase) {
    database.runInTransaction {
        database.clearAllTables()
        database.openHelper.writableDatabase.execSQL("DELETE FROM sqlite_sequence")
    }
}

// todo ↑↑↑ Хардкод, после настройки приложения удалить ↑↑↑