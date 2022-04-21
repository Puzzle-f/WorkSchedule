package com.example.workschedule.data

import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

class DomainRepositoryImpl(
    private val databaseRepository: DatabaseRepository
) : DomainRepository {

    override suspend fun saveNewPerson(personModel: DomainPersonModel) {
        databaseRepository.saveNewPerson(personModel)
    }

//    создание нового пользователя и его изменение - одна и та же функция
    override suspend fun changeDataPerson(personModel: DomainPersonModel) {
        databaseRepository.saveNewPerson(personModel)
    }

    override suspend fun delPerson(personModel: DomainPersonModel) {
        databaseRepository.delPerson(personModel)
    }




}