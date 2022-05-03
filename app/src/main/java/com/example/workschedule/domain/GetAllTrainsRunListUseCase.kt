package com.example.workschedule.domain

class GetAllTrainsRunListUseCase(
    private val repository: DomainRepository
) {
    fun execute() = repository.getAllTrainsRunList()
}