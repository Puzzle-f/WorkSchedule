package com.example.workschedule.ui.schedule_all_drivers.model

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import kotlinx.coroutines.flow.StateFlow

data class Vertical_RVModel (
    val driver: Driver,
//    val horizontalRVModel: StateFlow<List<Horizontal_RVModel>>
    val horizontalRVModel: List<Horizontal_RVModel>

)