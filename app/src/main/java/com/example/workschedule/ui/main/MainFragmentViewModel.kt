package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainFragmentViewModel : ViewModel() {

    private var _trains = MutableStateFlow<List<TestModelForMainAdapter>>(emptyList())
    val trains: StateFlow<List<TestModelForMainAdapter>> = _trains.asStateFlow()
}