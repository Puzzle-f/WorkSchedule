package com.example.workschedule.domain

import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

interface DomainRepository {

    fun saveNewPerson(personModel: DomainPersonModel)
}