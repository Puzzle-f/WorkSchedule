package com.example.workschedule.ui.finddriver

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverBeforeHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.fromDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectionDriverViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
    private val getTrainRunUseCase: GetTrainRunUseCase,
    private val findDriverBeforeHorizonUseCase: FindDriverBeforeHorizonUseCase,
    private val updateTrainRunUseCase: UpdateTrainRunUseCase,
    private val recalculateStatusesForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase
) : ViewModel() {

    private var _trainRun = MutableStateFlow<TrainRun?>(null)
    val trainRun: StateFlow<TrainRun?> = _trainRun.asStateFlow()

    private var _drivers = MutableStateFlow<List<Driver?>>(emptyList())
    val drivers: StateFlow<List<Driver?>> = _drivers.asStateFlow()

    private var _statuses = MutableStateFlow<List<Status>>(emptyList())
    val statuses: StateFlow<List<Status>> = _statuses.asStateFlow()

    private var _dataVisual = MutableStateFlow<List<SelectionDriverItemData>>(emptyList())
    val dataVisual: StateFlow<List<SelectionDriverItemData>> = _dataVisual.asStateFlow()

    fun getTrainRun(trainRunId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _trainRun.emit(getTrainRunUseCase.execute(trainRunId))
        }
    }

    fun getSelectionDriverData(trainRun: TrainRun) {
        val listSt = mutableListOf<Status>()
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) {
                findDriverBeforeHorizonUseCase.execute(
                    trainRun
                )
            })
            _statuses.emit(withContext(Dispatchers.IO) {
                drivers.value.forEach {
                    if (it != null) {
                        val el = getLastStatusUseCase.execute(it!!.id, trainRun.startTime)
                        if (el != null)
                            listSt.add(el.fromDTO)
                    }
                }
                listSt
            })
            combine(drivers, statuses) { dr, st ->
                val listData = mutableListOf<SelectionDriverItemData>()
                dr.forEach {
                    listData.add(
                        SelectionDriverItemData(
                            driverName = it?.surname,
                            if (it?.id != null) it.id else 0,
                            restTime = "0", workedTime = "0"
                        )
                    )
                }
                listData
            }.collect { _dataVisual.emit(withContext(Dispatchers.IO) { it }) }
        }
    }

    fun updateTrainRun(driverId: Int) {
        viewModelScope.launch {
            if (trainRun.value != null){
                val trainRunLocal = TrainRun(
                    trainRun.value!!.id,
                    trainRun.value!!.number,
                    driverId = driverId,
                    trainRun.value!!.direction,
                    trainRun.value!!.startTime,
                    trainRun.value!!.travelTime,
                    trainRun.value!!.countNight,
                    trainRun.value!!.workTime,
                    trainRun.value!!.periodicity,
                    trainRun.value!!.isEditManually,
                    trainRun.value!!.note
                )
                updateTrainRunUseCase.execute(trainRunLocal)
                recalculateStatusesForDriverAfterTimeUseCase.execute(driverId, trainRun.value!!.startTime)
            }
        }
    }

}