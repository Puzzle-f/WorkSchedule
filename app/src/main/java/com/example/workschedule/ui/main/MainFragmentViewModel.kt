package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.data.models.TrainRun
import com.example.workschedule.domain.GetAllTrainsRunListUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MainFragmentViewModel(
    getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase
) : ViewModel() {

    val trains: StateFlow<List<TrainRun>> =
        flow { emit(getAllTrainsRunListUseCase.execute()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )
}