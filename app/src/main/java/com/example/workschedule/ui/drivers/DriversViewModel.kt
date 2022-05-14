package com.example.workschedule.ui.drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.DeleteDriverUseCase
import com.example.workschedule.domain.GetAllDriversListUseCase
import com.example.workschedule.domain.driverList
import com.example.workschedule.domain.models.Driver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DriversViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteDriverUseCase: DeleteDriverUseCase
) : ViewModel() {

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(getAllDriversListUseCase.execute())
        }
    }

    fun deleteTrainRun(driverId: Int) {
        viewModelScope.launch {
            deleteDriverUseCase.execute(driverId)
        }
    }
}