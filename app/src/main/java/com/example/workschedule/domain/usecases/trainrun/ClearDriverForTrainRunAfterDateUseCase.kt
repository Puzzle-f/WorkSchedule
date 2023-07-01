package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ClearDriverForTrainRunAfterDateUseCase(private val repository: DomainRepository) {
    suspend fun execute(date: Long) = coroutineScope {
        launch(Dispatchers.IO) {
            repository.clearDriverForTrainRunAfterDate(date)
        }
    }

}