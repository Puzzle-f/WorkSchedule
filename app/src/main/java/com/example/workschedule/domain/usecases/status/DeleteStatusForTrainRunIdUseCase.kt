package com.example.workschedule.domain.usecases.status

import com.example.workschedule.domain.DomainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DeleteStatusForTrainRunIdUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRunId: Int) =
        coroutineScope {
            launch(Dispatchers.IO) {
                repository.deleteStatusForTrainRunId(trainRunId)
            }
        }
}