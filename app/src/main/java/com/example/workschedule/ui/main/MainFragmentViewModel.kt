package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.GetAllTrainsRunListUseCase
import com.example.workschedule.domain.models.DomainTrainRunModel
import kotlinx.coroutines.flow.*

class MainFragmentViewModel(
    getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase
) : ViewModel() {

    val trains: StateFlow<List<DomainTrainRunModel>> =
        flow { emit(getAllTrainsRunListUseCase.execute()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )
}