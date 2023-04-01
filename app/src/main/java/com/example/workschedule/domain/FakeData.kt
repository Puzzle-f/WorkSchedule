package com.example.workschedule.domain

import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import com.example.workschedule.utils.toDTO
import com.example.workschedule.utils.toLong
import java.time.LocalDateTime
import java.time.Month

// todo ↓↓↓ Хардкод, после настройки приложения удалить ↓↓↓

const val restHours = 16  // Константа опряделяющая количество часов отдыха после работы

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