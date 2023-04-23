package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.DomainRepository

class GetDriverByPersonalNumberAndSurname(
    private val repository: DomainRepository
) {
    suspend fun execute(personalNumber: Int, surname: String) = repository.getDriverByPersonalNumberAndSurname(personalNumber, surname)
}