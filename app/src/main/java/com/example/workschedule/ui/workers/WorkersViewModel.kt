package com.example.workschedule.ui.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.data.entities.Driver
import com.example.workschedule.utils.driverList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkersViewModel : ViewModel() {

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(driverListExample)
        }
    }
}