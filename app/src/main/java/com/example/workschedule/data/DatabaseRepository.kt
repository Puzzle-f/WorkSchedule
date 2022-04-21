package com.example.workschedule.data

import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

interface DatabaseRepository {

    suspend fun saveNewPerson(person: DomainPersonModel)
    suspend fun delPerson(personModel: DomainPersonModel)
}