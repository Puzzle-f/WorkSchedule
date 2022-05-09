package com.example.workschedule.domain

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import java.time.LocalDateTime
import java.time.Month

// todo ↓↓↓ Хардкод, после настройки приложения удалить ↓↓↓

const val restHours = 16  // Константа опряделяющая количество часов отдыха после работы

val trainRunList = listOf(
    TrainRun(
        1, 120, "Москва", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
        8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        2, 14, "Ростов-на-Дону", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 12, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        3, 92, "Санкт-Петербург", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 16, 30),
        5.hoursToMillis, 5.hoursToMillis, 5.hoursToMillis
    ),
    TrainRun(
        4, 32, "Краснодар", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 3, 30),
        6.hoursToMillis, 8.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        5, 51, "Воронеж", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 6, 30),
        7.hoursToMillis, 4.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        6, 96, "Туапсе", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 22, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        7, 72, "Ставрополь", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 30),
        7.hoursToMillis, 4.hoursToMillis, 7.hoursToMillis
    ),
    TrainRun(
        8, 120, "Москва", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 9.hoursToMillis
    ),
    TrainRun(
        9, 99, "Нижний Новгород", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    )
)

val driverList = listOf(
    Driver(1, "Иван", "Иванов", "Иванович", 5, 10, listOf(120, 92, 14)),
    Driver(2, "Олег", "Петров", "Дмитриевич", 3, 2, listOf(32, 14, 51)),
    Driver(3, "Василий", "Запойный", "Филиппов", 100, 2, listOf(51, 72, 80)),
    Driver(4, "Николай", "Тимченко", "Петрович", 0, 0, listOf(96, 72)),
    Driver(5, "Семен", "Слепаков", "Владимирович", 10, 1, listOf(90, 99)),
    Driver(6, "Иннокентий", "Пронин", "Юрьевич", 13, 2, listOf(103, 11, 125, 96)),
    Driver(7, "Дмитрий", "Гуськов", "Зурабович", 7, 3, listOf(72, 51, 92)),
    Driver(8, "Вячеслав", "Зайцев", "Зиновьевич", 16, 1, listOf(92, 96)),
    Driver(9, "Александр", "Сотников", "Александрович", 8, 9, listOf(120, 92)),
    Driver(10, "Андрей", "Пронин", "Юрьевич", 8, 4, listOf(72, 32, 11)),
    Driver(11, "Владимир", "Ясный", "Егорович", 15, 2, listOf(103, 14, 99)),
    Driver(12, "Павел", "Причудный", "Владимирович", 7, 0, listOf(125, 14)),
    Driver(13, "Леонид", "Свистунов", "Геннадиевич", 5, 6, listOf(11, 99, 32)),
    Driver(14, "Алексей", "Балабанов", "Михайлович", 0, 19, listOf(14, 80, 11)),
    Driver(15, "Юрий", "Дудь", "Семёнович", 0, 54, listOf(99, 92, 96)),
    Driver(16, "Виталий", "Кличко", "Леонидович", 21, 0, listOf(32, 96, 11)),
    Driver(17, "Всеволод", "Чаплин", "Кириллович", 3, 0, listOf(120, 92)),
    Driver(18, "Никита", "Бесстрашная", "Харвиевна", 85, 6, listOf(103, 14)),
    Driver(19, "Виктор", "Пелевин", "Алексеевич", 65, 1, listOf(72, 99)),
    Driver(20, "Евлампий", "Романов", "Булатович", 8, 2, listOf(32, 92, 72))
)

val trainList = listOf(
    Train(120, "Москва"),
    Train(92, "Санкт-Петербург"),
    Train(32, "Краснодар"),
    Train(14, "Ростов-на-Дону"),
    Train(51, "Воронеж"),
    Train(96, "Туапсе"),
    Train(72, "Ставрополь"),
    Train(80, "Тюмень"),
    Train(99, "Нижний Новгород"),
    Train(103, "Новороссийск"),
    Train(11, "Казань"),
    Train(125, "Киров")
)

// todo ↑↑↑ Хардкод, после настройки приложения удалить ↑↑↑
