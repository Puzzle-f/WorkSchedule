package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository

class DeleteAllDriversUseCase(
    val repository: DomainRepository
) {
    suspend fun execute(){
        repository.deleteAllDriversList()
    }
}