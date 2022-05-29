package com.example.workschedule.domain

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.usecases.driver.SaveDriverUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveDriverUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = SaveDriverUseCase(domainRepository)
        val driver = Driver(
            id = 0,
            personnelNumber = 15,
            surname = "Иванов",
            name = "Иван",
            patronymic = "Иванович",
            workedTime = 5,
            totalTime = 10,
            accessTrainsId = listOf(1, 4, 2)
        )
        runBlocking {
            useCase.execute(driver)
            Mockito.verify(domainRepository, Mockito.times(1)).saveDriver(driver)
        }
    }
}