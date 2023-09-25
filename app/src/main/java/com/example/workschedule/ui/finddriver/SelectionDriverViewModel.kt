package com.example.workschedule.ui.finddriver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.logiс.CleanDriverForIntersectionsUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverBeforeHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.SetDriverToTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.ui.settings.CHECK_WEEKENDS
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
    private val recalculateStatusesForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val cleanDriverForRange: CleanDriverForIntersectionsUseCase,
    private val setDriverToTrainRunUseCase: SetDriverToTrainRunUseCase
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
                findDriverBeforeHorizonUseCase.execute(trainRun, CHECK_WEEKENDS)
            })
            _statuses.emit(withContext(Dispatchers.IO) {
                drivers.value.forEach {
                    if (it != null) {
                        val el = getLastStatusUseCase.execute(it.id, trainRun.startTime)
                        if (el != null)
                            listSt.add(el.fromDTO)
                    }
                }
                listSt
            })
            combine(drivers, statuses) { dr, st ->
                val listData = mutableListOf<SelectionDriverItemData>()
                dr.forEach { driver ->
//                    val statusLoc = st.first { it.idDriver == driver!!.id }
//                    val restTime = (trainRun.startTime - statusLoc.date).toHoursTimeString
//                    val workedTime = statusLoc.workedTime
                    listData.add(
                        SelectionDriverItemData(
                            driverName = driver?.surname,
                            if (driver?.id != null) driver.id else 0,
//                            restTime = restTime, workedTime = workedTime.toHoursTimeString
                            restTime = "0", workedTime = "0"
                        )
                    )
                }
                listData
            }.collect { _dataVisual.emit(withContext(Dispatchers.IO) { it }) }
        }
    }

    fun updateTrainRun(driverId: Int) =
        viewModelScope.launch {
            if (trainRun.value != null) {
                setDriverToTrainRunUseCase.execute(trainRun.value!!.id, driverId)
                trainRun.value!!.driverId = driverId
                recalculateStatusesForDriverAfterTimeUseCase.execute(
                    driverId,
                    trainRun.value!!.startTime
                )
            }
        }

    fun cleanDriverForTrainRun(driverId: Int) {
        viewModelScope.launch {
            cleanDriverForRange.execute(trainRun.value!!, driverId)
        }
    }

}