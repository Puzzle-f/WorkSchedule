package com.example.workschedule.domain

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.data.database.train.TrainEntity
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
    Driver(1, "Иванов", "Иван", "Иванович", 5, 10, listOf(120, 92, 14)),
    Driver(2, "Петров", "Олег", "Дмитриевич", 3, 2, listOf(32, 14, 51)),
    Driver(3, "Запойный", "Василий", "Филиппов", 100, 2, listOf(51, 72, 80)),
    Driver(4, "Тимченко", "Николай", "Петрович", 0, 0, listOf(96, 72)),
    Driver(5, "Слепаков", "Семен", "Владимирович", 10, 1, listOf(90, 99)),
    Driver(6, "Пронин", "Иннокентий", "Юрьевич", 13, 2, listOf(103, 11, 125, 96)),
    Driver(7, "Гуськов", "Дмитрий", "Зурабович", 7, 3, listOf(72, 51, 92)),
    Driver(8, "Зайцев", "Вячеслав", "Зиновьевич", 16, 1, listOf(92, 96)),
    Driver(9, "Сотников", "Александр", "Александрович", 8, 9, listOf(120, 92)),
    Driver(10, "Пронин", "Андрей", "Юрьевич", 8, 4, listOf(72, 32, 11)),
    Driver(11, "Ясный", "Владимир", "Егорович", 15, 2, listOf(103, 14, 99)),
    Driver(12, "Причудный", "Павел", "Владимирович", 7, 0, listOf(125, 14)),
    Driver(13, "Свистунов", "Леонид", "Геннадиевич", 5, 6, listOf(11, 99, 32)),
    Driver(14, "Балабанов", "Алексей", "Михайлович", 0, 19, listOf(14, 80, 11)),
    Driver(15, "Дудь", "Юрий", "Семёнович", 0, 54, listOf(99, 92, 96)),
    Driver(16, "Кличко", "Виталий", "Леонидович", 21, 0, listOf(32, 96, 11)),
    Driver(17, "Чаплин", "Всеволод", "Кириллович", 3, 0, listOf(120, 92)),
    Driver(18, "Бесстрашная", "Никита", "Харвиевна", 85, 6, listOf(103, 14)),
    Driver(19, "Пелевин", "Виктор", "Алексеевич", 65, 1, listOf(72, 99)),
    Driver(20, "Романов", "Евлампий", "Булатович", 8, 2, listOf(32, 92, 72))
)

val trainList = listOf(
    Train(0, 120, "Москва"),
    Train(0, 92, "Санкт-Петербург"),
    Train(0, 32, "Краснодар"),
    Train(0, 14, "Ростов-на-Дону"),
    Train(0, 51, "Воронеж"),
    Train(0, 96, "Туапсе"),
    Train(0, 72, "Ставрополь"),
    Train(0, 80, "Тюмень"),
    Train(0, 99, "Нижний Новгород"),
    Train(0, 103, "Новороссийск"),
    Train(0, 11, "Казань"),
    Train(0, 125, "Киров")
)

suspend fun saveFakeDataToDB(dataBase: ScheduleDataBase) {
    trainList.forEach {
        dataBase.trainDao().saveOrChangeTrain(
            TrainEntity(it.id, it.number, it.direction)
        )
    }
    driverList.forEach {
        dataBase.driverDao().saveOrChange(
            DriverEntity(
                it.id,
                it.name,
                it.surname,
                it.patronymic,
                it.workedTime,
                it.totalTime,
                it.accessTrainsId
            )
        )
    }
    trainRunList.forEach {
    }
}

// todo ↑↑↑ Хардкод, после настройки приложения удалить ↑↑↑
