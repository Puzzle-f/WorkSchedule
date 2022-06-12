package com.example.workschedule.domain

import com.example.workschedule.domain.usecases.driver.DeleteDriverUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteDriverUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = DeleteDriverUseCase(domainRepository)
        val driverId = 1
        runBlocking {
            useCase.execute(driverId)
            Mockito.verify(domainRepository, Mockito.times(1)).deleteDriver(driverId)
        }
    }
}