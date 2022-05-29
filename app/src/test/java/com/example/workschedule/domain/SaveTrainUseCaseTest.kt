package com.example.workschedule.domain

import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.usecases.train.SaveTrainUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveTrainUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = SaveTrainUseCase(domainRepository)
        val train = Train(id = 1, direction = "Moscow")
        runBlocking {
            useCase.execute(train)
            Mockito.verify(domainRepository, Mockito.times(1)).saveTrain(train)
        }
    }
}