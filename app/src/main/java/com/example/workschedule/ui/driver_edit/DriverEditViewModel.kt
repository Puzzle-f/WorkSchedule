package com.example.workschedule.ui.driver_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.usecases.train.GetAllTrainsListUseCase
import com.example.workschedule.domain.usecases.driver.GetDriverUseCase
import com.example.workschedule.domain.usecases.driver.SaveDriverUseCase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DriverEditViewModel(
    private val getDriverUseCase: GetDriverUseCase,
    private val getAllTrainsListUseCase: GetAllTrainsListUseCase,
    private val saveDriverUseCase: SaveDriverUseCase
) : ViewModel() {
    private var _driver = MutableStateFlow<Driver?>(null)
    val driver: StateFlow<Driver?> = _driver.asStateFlow()
    private var _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun getDriver(driverId: Int) {
        viewModelScope.launch {
            _driver.emit(withContext(Dispatchers.IO) { getDriverUseCase.execute(driverId) })
        }
    }

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(withContext(Dispatchers.IO) { getAllTrainsListUseCase.execute() })
        }
    }

    fun saveDriver(driver: Driver) {
        viewModelScope.launch(Dispatchers.IO) {
            saveDriverUseCase.execute(driver)
        }
    }
}
