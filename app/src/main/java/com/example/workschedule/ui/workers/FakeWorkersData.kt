package com.example.workschedule.ui.workers

import com.example.workschedule.data.entities.Driver

fun fakeWorkersData(): List<Driver> {
    val data = mutableListOf<Driver>()
    for (i in 1..10) {
        data.add(
            Driver(
                id = i,
                name = "name$i",
                surname = "surname$i",
                patronymic = "patronymic$i",
                workedTime = i.toLong(),
                totalTime = i.toLong(),
                accessTrainsId = mutableListOf(i, i + 1, i + 2)
            )
        )
    }
    return data
}

val driverListExample = listOf(
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
    Driver(13,"Леонид", "Свистунов", "Геннадиевич", 5, 6, listOf(11, 99, 32)),
    Driver(14,"Алексей", "Балабанов", "Михайлович", 0, 19, listOf(14, 80, 11)),
    Driver(15,"Юрий", "Дудь", "Семёнович", 0, 54, listOf(99, 92, 96)),
    Driver(16,"Виталий", "Кличко", "Леонидович", 21, 0, listOf(32, 96, 11)),
    Driver(17,"Всеволод", "Чаплин", "Кириллович", 3, 0, listOf(120, 92)),
    Driver(18,"Никита", "Бесстрашная", "Харвиевна", 85, 6, listOf(103, 14)),
    Driver(19,"Виктор", "Пелевин", "Алексеевич", 65, 1, listOf(72, 99)),
    Driver(20,"Евлампий", "Романов", "Булатович", 8, 2, listOf(32, 92, 72))
)
