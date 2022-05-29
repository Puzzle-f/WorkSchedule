package com.example.workschedule.domain

import com.example.workschedule.domain.usecases.train.DeleteTrainUseCase
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
        val useCase = DeleteTrainUseCase(domainRepository)
        val trainId = 1
        runBlocking {
            useCase.execute(trainId)
            Mockito.verify(domainRepository, Mockito.times(1)).deleteTrain(trainId)
        }
    }
}