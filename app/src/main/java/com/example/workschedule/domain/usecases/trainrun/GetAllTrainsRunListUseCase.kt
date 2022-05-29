package com.example.workschedule.domain.usecases.trainrun

import com.example.workschedule.domain.DomainRepository

class GetAllTrainsRunListUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute() = repository.getAllTrainsRunList()
}