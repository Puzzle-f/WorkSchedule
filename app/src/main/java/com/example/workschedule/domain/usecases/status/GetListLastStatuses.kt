package com.example.workschedule.domain.usecases.status

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status

class GetListLastStatuses(
    private val repository: DomainRepository
) {
//    suspend fun List<Driver>.execute(): List<Status> =
//        repository.getListLastStatuses(this)
}