package com.example.workschedule.ui.drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.usecases.driver.DeleteDriverUseCase
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.usecases.driver.DeleteAllDriversUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DriversViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteDriverUseCase: DeleteDriverUseCase,
    private val deleteAllDriversUseCase: DeleteAllDriversUseCase
) : ViewModel() {

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
        }
    }

    fun deleteDriver(driverId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteDriverUseCase.execute(driverId)
        }
    }

    fun deleteAllDrivers(){
        viewModelScope.launch {
            deleteAllDriversUseCase.execute()
        }
    }
}