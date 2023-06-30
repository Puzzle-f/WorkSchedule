package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteAllTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.GetAllTrainsRunListUseCase
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel(
    private val getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteTrainRunUseCase: DeleteTrainRunUseCase,
    private val deleteAllTrainRunUseCase: DeleteAllTrainRunUseCase,
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForForDriverAfterTimeUseCase,
    private val findDriverUseCase: FindDriverUseCase
) : ViewModel() {

    private var _trainRunList = MutableStateFlow<List<TrainRun>>(emptyList())
    val trainRunList: StateFlow<List<TrainRun>> = _trainRunList.asStateFlow()

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()

    private var _directions = MutableStateFlow<List<Direction>>(emptyList())
    val directions: StateFlow<List<Direction>> = _directions.asStateFlow()

    private var _data = MutableStateFlow<List<MainFragmentData>>(emptyList())
    val data: StateFlow<List<MainFragmentData>> = _data.asStateFlow()

    fun getMainFragmentData() {
        viewModelScope.launch {
            _trainRunList.emit(withContext(Dispatchers.IO) { getAllTrainsRunListUseCase.execute() })
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
            combine(trainRunList, drivers, directions) { trainRunIt, driversIt, directionsIt ->
                val listData = mutableListOf<MainFragmentData>()
                trainRunIt.forEach { trainRunThis ->
                    val driverId = if (trainRunThis.driverId != 0)
                        driversIt.first { it.id == trainRunThis.driverId }.surname else 0
                    val dataElement = MainFragmentData(
                        trainRunThis.id,
                        trainRunThis.startTime.toLocalDateTime().toLocalDate().toString(),
                        trainRunThis.startTime.toLocalDateTime().toLocalTime().toString(),
                        trainRunThis.number.toInt(),
                        directionsIt.first { it.id == trainRunThis.direction }.name,
                        driverId.toString(),
                        trainRunThis.travelTime.toTimeString,
                        trainRunThis.workTime.toTimeString,
                        trainRunThis.countNight,
                        trainRunThis.isEditManually
                    )
                    listData.add(dataElement)
                }
                listData
            }.collect { _data.emit(withContext(Dispatchers.IO) { it }) }
        }
    }

    fun deleteTrainRun(trainRunId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val trainRun = trainRunList.value.find { it.id == trainRunId }
            deleteTrainRunUseCase.execute(trainRun!!.id)
            recalculateStatusesForForDriverAfterTimeUseCase.execute(
                trainRun.driverId,
                trainRun.startTime
            )
        }
    }

    fun findDriver() =
        viewModelScope.launch(Dispatchers.IO) {
            trainRunList.value.forEach {
                if (it.driverId == 0)
                    findDriverUseCase.execute(it).join()
            }
        }


    fun deleteAllTrainRun() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTrainRunUseCase.execute()
        }
    }
}