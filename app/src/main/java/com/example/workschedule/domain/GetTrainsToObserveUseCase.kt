package com.example.workschedule.domain

import com.example.workschedule.ui.main.TestModelForMainAdapter
import kotlinx.coroutines.flow.flow

class GetTrainsToObserveUseCase(
    // TODO: добавить репозиторий после тестов
) {
    fun testExecute() = flow {
        emit(
            listOf(
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
                TestModelForMainAdapter(),
            )
        )
    }
}