package com.example.workschedule.domain.models

data class DomainDriverModel(
    var id: Int,
    var name: String,
    var surname: String,
    var patronymic: String,
    var workedTime: Long,
    var totalTime: Long,
    var accessTrainsId: List<Int>
)
