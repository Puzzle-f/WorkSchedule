package com.example.workschedule.domain.domainpersonmodel

import com.example.workschedule.domain.models.direction.DomainPathDirectionModel

data class DomainPersonModel(
    val firstName: String,
    val secondName: String,
    val thirdName: String?,
    val hoursWorked: Int,
    val daysOff: List<Long>?,
    val pathDirections: List<DomainPathDirectionModel>
)