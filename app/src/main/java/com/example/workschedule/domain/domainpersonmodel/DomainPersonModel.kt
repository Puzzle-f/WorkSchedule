package com.example.workschedule.domain.domainpersonmodel

data class DomainPersonModel(
    val firstName: String,
    val secondName: String,
    val thirdName: String?,
    val hoursWorked: Int,
    val daysOff: List<DomainDayOffModel>,
    val pathDirections: List<DomainPathDirectionModel>,
)