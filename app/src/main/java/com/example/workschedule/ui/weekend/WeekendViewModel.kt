package com.example.workschedule.ui.weekend

import androidx.lifecycle.ViewModel
import com.example.workschedule.domain.usecases.weekend.GetWeekendsUseCase
import com.example.workschedule.domain.usecases.weekend.SaveWeekendUseCase

class WeekendViewModel(
    private val getWeekends: GetWeekendsUseCase,
    private val saveWeekends: SaveWeekendUseCase
): ViewModel() {



}