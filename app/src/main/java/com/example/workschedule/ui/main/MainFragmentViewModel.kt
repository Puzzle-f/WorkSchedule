package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.GetTrainsToObserveUseCase
import kotlinx.coroutines.flow.*

class MainFragmentViewModel(
    getTrainsToObserveUseCase: GetTrainsToObserveUseCase = GetTrainsToObserveUseCase()
) : ViewModel() {

    val trains: StateFlow<List<TestModelForMainAdapter>> = getTrainsToObserveUseCase.testExecute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
}