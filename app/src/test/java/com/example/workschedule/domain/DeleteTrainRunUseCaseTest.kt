package com.example.workschedule.domain

import com.example.workschedule.domain.usecases.trainrun.DeleteTrainRunUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteTrainRunUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

    @Test
    fun should_invoke_one_time() {
        val useCase = DeleteTrainRunUseCase(domainRepository)
        val trainRunId = 1
        runBlocking {
            useCase.execute(trainRunId)
            Mockito.verify(domainRepository, Mockito.times(1)).deleteTrainRun(trainRunId)
        }
    }
}