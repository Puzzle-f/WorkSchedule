package com.example.workschedule.ui.schedule_all_drivers.adapters_model

import com.example.workschedule.domain.models.Driver

data class VerticalRVModel(
    val driver: Driver,
    val horizontalRVModel: List<HorizontalRVModel>
)