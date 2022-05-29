package com.example.workschedule.domain

import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunUseCase
import com.example.workschedule.utils.hoursToMillis
import com.example.workschedule.utils.minutesToMillis
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.time.LocalDateTime
import java.time.Month

class SaveTrainRunUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = SaveTrainRunUseCase(domainRepository)
        val trainRun = TrainRun(
            1, 1, 120, "Москва", 0, "",
            LocalDateTime.of(2022, Month.APRIL, 1, 6, 30),
            8.hoursToMillis + 25.minutesToMillis, 6.hoursToMillis, 8.hoursToMillis
        )
        runBlocking {
            useCase.execute(trainRun)
            Mockito.verify(domainRepository, Mockito.times(1)).saveTrainRun(trainRun)
        }
    }
}