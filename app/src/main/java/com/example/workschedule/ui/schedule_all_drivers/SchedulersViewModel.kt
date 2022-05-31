package com.example.workschedule.ui.schedule_all_drivers

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.GetAllDriversListUseCase
import com.example.workschedule.domain.GetTrainRunListForDriverUseCase
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.schedule_all_drivers.model.Horizontal_RVModel
import com.example.workschedule.ui.schedule_all_drivers.model.Vertical_RVModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SchedulersViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getTrainRunListForDriverUseCase: GetTrainRunListForDriverUseCase
) : ViewModel() {

    var listHorizontal_RVModel = mutableListOf<Horizontal_RVModel>()

    private var _data = MutableStateFlow<List<Vertical_RVModel>>(emptyList())
    val data: StateFlow<List<Vertical_RVModel>> = _data.asStateFlow()

    private var _trains = MutableStateFlow<List<TrainRun>>(emptyList())
    val trains: StateFlow<List<TrainRun>> = _trains.asStateFlow()

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(getAllDriversListUseCase.execute())
        }
    }

    fun getVerticalRVModel() {
        getDrivers()
        viewModelScope.launch {
            val l = mutableListOf<Vertical_RVModel>()
            for (i in drivers.value) {
                println(i.surname)
                trainRunToHorizontalRVModel(i)
                l.add(
                    Vertical_RVModel(
                        i, listHorizontal_RVModel
                    )
                )
            }
            _data.emit(l)
            println("l = $l")
        }
    }


    fun trainRunToHorizontalRVModel(driver: Driver) {
        Log.d("", "**** trainRunToHorizontalRVModel()")
        viewModelScope.launch {
            _trains.emit(getTrainRunListForDriverUseCase.execute(driver.id))
            listHorizontal_RVModel.clear()
            for (i in trains.value) {
                listHorizontal_RVModel.add(
                    Horizontal_RVModel(
                        i.startTime.toString(),
                        i.trainNumber.toString()
                    )
                )
            }
        }
    }
}

