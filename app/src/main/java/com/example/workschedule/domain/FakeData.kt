package com.example.workschedule.domain

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import com.example.workschedule.utils.toDAO
import java.time.LocalDateTime
import java.time.Month

// todo ↓↓↓ Хардкод, после настройки приложения удалить ↓↓↓

const val restHours = 16  // Константа опряделяющая количество часов отдыха после работы


val trainList = listOf(
    Train(1, "Москва"),
    Train(2, "Санкт-Петербург"),
    Train(3, "Краснодар"),
    Train(4, "Ростов-на-Дону"),
    Train(5, "Воронеж"),
    Train(6, "Туапсе"),
    Train(7, "Ставрополь"),
    Train(8, "Тюмень"),
    Train(9, "Нижний Новгород"),
    Train(10, "Новороссийск"),
    Train(11, "Казань"),
    Train(12, "Киров")
)

val driverList = listOf(
    Driver(0, 15, "Иванов", "Иван", "Иванович", 5, 10, listOf(1, 4, 2)),
    Driver(0, 17, "Петров", "Олег", "Дмитриевич", 3, 2, listOf(3, 2, 5)),
    Driver(0, 12, "Запойный", "Василий", "Филиппов", 100, 2, listOf(5, 7, 11)),
    Driver(0, 11, "Тимченко", "Николай", "Петрович", 0, 0, listOf(6, 7)),
    Driver(0, 1, "Слепаков", "Семен", "Владимирович", 10, 1, listOf(8, 9)),
    Driver(0, 18, "Пронин", "Иннокентий", "Юрьевич", 13, 2, listOf(8, 11, 12, 6)),
    Driver(0, 21, "Гуськов", "Дмитрий", "Зурабович", 7, 3, listOf(7, 5, 4)),
    Driver(0, 31, "Зайцев", "Вячеслав", "Зиновьевич", 16, 1, listOf(4, 6)),
    Driver(0, 61, "Сотников", "Александр", "Александрович", 8, 9, listOf(1, 4)),
    Driver(0, 23, "Пронин", "Андрей", "Юрьевич", 8, 4, listOf(7, 3, 11)),
    Driver(0, 25, "Ясный", "Владимир", "Егорович", 15, 2, listOf(8, 2, 9)),
    Driver(0, 36, "Причудный", "Павел", "Владимирович", 7, 0, listOf(12, 10)),
    Driver(0, 51, "Свистунов", "Леонид", "Геннадиевич", 5, 6, listOf(11, 9, 3)),
    Driver(0, 55, "Балабанов", "Алексей", "Михайлович", 0, 19, listOf(10, 11, 12)),
    Driver(0, 40, "Дудь", "Юрий", "Семёнович", 0, 54, listOf(9, 4, 6)),
    Driver(0, 30, "Кличко", "Виталий", "Леонидович", 21, 0, listOf(3, 6, 11)),
    Driver(0, 22, "Чаплин", "Всеволод", "Кириллович", 3, 0, listOf(1, 4)),
    Driver(0, 29, "Бесстрашная", "Никита", "Харвиевна", 85, 6, listOf(8, 2)),
    Driver(0, 39, "Пелевин", "Виктор", "Алексеевич", 65, 1, listOf(7, 9)),
    Driver(0, 77, "Романов", "Евлампий", "Булатович", 8, 2, listOf(3, 4, 7))
)

val trainRunList = listOf(
    TrainRun(
        1, 1, 120, "Москва", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
        8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        2, 4, 48, "Ростов-на-Дону", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 12, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        3, 2, 92, "Санкт-Петербург", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 1, 16, 30),
        5.hoursToMillis, 5.hoursToMillis, 5.hoursToMillis
    ),
    TrainRun(
        4, 3, 32, "Краснодар", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 3, 30),
        6.hoursToMillis, 8.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        5, 5, 51, "Воронеж", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 6, 30),
        7.hoursToMillis, 4.hoursToMillis, 6.hoursToMillis
    ),
    TrainRun(
        6, 6, 96, "Туапсе", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 2, 22, 30),
        8.hoursToMillis, 4.hoursToMillis, 8.hoursToMillis
    ),
    TrainRun(
        7, 7, 72, "Ставрополь", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 2, 30),
        7.hoursToMillis, 4.hoursToMillis, 7.hoursToMillis
    ),
    TrainRun(
        8, 1, 120, "Москва", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 6, 30),
        8.hoursToMillis, 4.hoursToMillis, 9.hoursToMillis
    ),
    TrainRun(
        9, 9, 99, "Нижний Новгород", 0, "",
        LocalDateTime.of(2022, Month.APRIL, 3, 8, 30),
        13.hoursToMillis, 4.hoursToMillis, 13.hoursToMillis + 23.minutesToMillis
    )
)

// Метод записи хард-кода в Базу Данных для демонстрации
suspend fun saveFakeDataToDB(database: ScheduleDataBase) {
    trainList.forEach { database.trainDao().saveTrain(it.toDAO) }
    driverList.forEach { database.driverDao().saveDriver(it.toDAO) }
    trainRunList.forEach { database.trainRunDao().saveTrainRun(it.toDAO) }
}

// Метод очистки базы данных с очисткой ключей автоинкремента
fun clearDatabase(database: ScheduleDataBase) {
    database.runInTransaction {
        database.clearAllTables()
        database.openHelper.writableDatabase.execSQL("DELETE FROM sqlite_sequence")
    }
}

// todo ↑↑↑ Хардкод, после настройки приложения удалить ↑↑↑
