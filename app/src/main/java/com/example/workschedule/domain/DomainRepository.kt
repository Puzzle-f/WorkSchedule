package com.example.workschedule.domain

import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

interface DomainRepository {

    suspend fun saveNewPerson(personModel: DomainPersonModel)
    suspend fun delPerson(personModel: DomainPersonModel)
    suspend fun changeDataPerson(personModel: DomainPersonModel)
}