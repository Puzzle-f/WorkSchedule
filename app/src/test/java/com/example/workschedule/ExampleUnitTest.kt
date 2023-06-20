package com.example.workschedule

import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun

import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import java.time.Month

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun expectedWorkNightsIsCorrected(){
//        val trainRun = TrainRun(
//            1, 1, 120, "Лиски", TrainPeriodicity.SINGLE, 0, "",
//            LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
//            8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis, false
//        )
//        assertEquals(1, 1)
    }
}