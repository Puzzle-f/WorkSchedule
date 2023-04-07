package com.example.workschedule.domain

import com.example.workschedule.domain.usecases.train.DeleteDirectionUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteTrainUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = DeleteDirectionUseCase(domainRepository)
        val trainId = 1
        runBlocking {
            useCase.execute(trainId)
            Mockito.verify(domainRepository, Mockito.times(1)).deleteDirection(trainId)
        }
    }
}