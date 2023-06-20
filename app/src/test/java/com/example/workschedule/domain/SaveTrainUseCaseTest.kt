package com.example.workschedule.domain

import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.usecases.train.SaveDirectionUseCase
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
//        val useCase = SaveDirectionUseCase(domainRepository)
//        val direction = Direction(idDirection = 1, nameDirection = "Moscow")
//        runBlocking {
//            useCase.execute(direction)
//            Mockito.verify(domainRepository, Mockito.times(1)).saveDirection(direction)
//        }
    }
}