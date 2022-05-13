package com.example.workschedule.ui.route_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.GetAllDriversListUseCase
import com.example.workschedule.domain.GetAllTrainsListUseCase
import com.example.workschedule.domain.SaveTrainRunUseCase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainRun
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RouteEditViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getAllTrainsListUseCase: GetAllTrainsListUseCase,
    private val saveTrainRunUseCase: SaveTrainRunUseCase
) : ViewModel() {
    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()
    private var _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(getAllDriversListUseCase.execute())
        }
    }

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(getAllTrainsListUseCase.execute())
        }
    }

    fun saveTrainRun(trainRun: TrainRun) {
        viewModelScope.launch {
            saveTrainRunUseCase.execute(trainRun)
        }
    }
}
