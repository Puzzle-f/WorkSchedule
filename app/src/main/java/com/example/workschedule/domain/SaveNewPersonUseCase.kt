package com.example.workschedule.domain

import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

class SaveNewPersonUseCase(
    private val domainRepository: DomainRepository
) {
    fun execute(personModel: DomainPersonModel) = domainRepository.saveNewPerson(personModel)
}