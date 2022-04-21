package com.example.workschedule.domain.domainpersonmodel

import com.example.workschedule.data.database.personal.PersonalEntity

data class DomainPersonModel(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val thirdName: String?,
    val hoursWorked: Int,
    val ifWork: Boolean,
    val ifResting: Boolean,
//    val daysOff: List<DomainDayOffModel>,
//    val permissions: HashMap<String, Boolean>
){
    fun mapToPersonalEntity() = PersonalEntity(
        id,
        firstName,
        secondName,
        thirdName,
        hoursWorked,
        ifWork,
        ifResting
    )
}