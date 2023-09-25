package com.example.workschedule.domain.models

/**
* класс описывает отвлечения от работы (командировка,
* больничный)
* @param isDistracted true - на отвлечении от работы
*                          false - в работе, конец отвлечения
* */

data class Distraction (
    val driverId: Int,
    val date: Long,
    val isDistracted: Boolean
        )