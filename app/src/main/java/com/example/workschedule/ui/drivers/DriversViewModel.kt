package com.example.workschedule.ui.workers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.DomainDriverModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkersViewModel : ViewModel() {

    private var _drivers = MutableStateFlow<List<DomainDriverModel>>(emptyList())
    val drivers: StateFlow<List<DomainDriverModel>> = _drivers.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(driverListExample)
        }
    }
}
