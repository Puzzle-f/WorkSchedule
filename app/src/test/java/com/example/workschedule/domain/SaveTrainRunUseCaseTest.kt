package com.example.workschedule.domain

import org.junit.After
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveTrainRunUseCaseTest {

    private val domainRepository = mock<DomainRepository>()

    @After
    fun tearDown() {
        Mockito.reset(domainRepository)
    }

}