package com.example.workschedule.ui.trainrun_edit

import com.example.workschedule.domain.models.TrainPeriodicity

data class TrainRunEditVisual(
    val number: String,
    var driver: String?,
    val direction: String,
    val startTime: Long,
    val travelTime: String,
    val countNight: Int,
    val workTime: String,
    val periodicity: TrainPeriodicity,
    val isEditManually: Boolean,
    val note: String?
)