package com.example.workschedule.data

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

class DomainRepositoryImpl(
    private val databaseRepository: DatabaseRepository
) : DomainRepository {

    override fun saveNewPerson(personModel: DomainPersonModel) {
        databaseRepository.saveNewPerson()
    }
}