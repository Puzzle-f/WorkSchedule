package com.example.workschedule.data.database

import com.example.workschedule.data.DatabaseRepository
import com.example.workschedule.data.database.personal.PersonalDao
import com.example.workschedule.data.database.personal.PersonalDataBase
import com.example.workschedule.data.database.personal.PersonalEntity
import com.example.workschedule.domain.domainpersonmodel.DomainPersonModel

class DatabaseRepositoryImpl(val db: PersonalDataBase): DatabaseRepository {

    override suspend fun saveNewPerson(personModel: DomainPersonModel) {
        db.personalDao().insert(personModel.mapToPersonalEntity())
    }

    override suspend fun delPerson(personModel: DomainPersonModel) {
        db.personalDao().delete(personModel.mapToPersonalEntity())
    }








}