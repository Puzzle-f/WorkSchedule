package com.example.workschedule.ui.finddriver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.logiс.CleanDriverForIntersectionsUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverBeforeHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.domain.usecases.status.GetStatusCompletionTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.SetDriverToTrainRunUseCase
import com.example.workschedule.ui.settings.CHECK_WEEKENDS
import com.example.workschedule.utils.FIO
import com.example.workschedule.utils.fromDTO
import com.example.workschedule.utils.toHoursTimeString
import com.example.workschedule.utils.toLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class SelectionDriverViewModel(
    private val getTrainRunUseCase: GetTrainRunUseCase,
    private val findDriverBeforeHorizonUseCase: FindDriverBeforeHorizonUseCase,
    private val recalculateStatusesForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val cleanDriverForRange: CleanDriverForIntersectionsUseCase,
    private val setDriverToTrainRunUseCase: SetDriverToTrainRunUseCase,
    private val getStatusCompletionTrainRunUseCase: GetStatusCompletionTrainRunUseCase
) : ViewModel() {

    private var _trainRun = MutableStateFlow<TrainRun?>(null)
    val trainRun: StateFlow<TrainRun?> = _trainRun.asStateFlow()

    private var _drivers = MutableStateFlow<List<Driver?>>(emptyList())
    val drivers: StateFlow<List<Driver?>> = _drivers.asStateFlow()

    private var _statuses = MutableStateFlow<List<Status>>(emptyList())
    val statuses: StateFlow<List<Status>> = _statuses.asStateFlow()

    private var _dataVisual = MutableStateFlow<List<SelectionDriverItemData>>(emptyList())
    val dataVisual: StateFlow<List<SelectionDriverItemData>> = _dataVisual.asStateFlow()

    private var _showProgress = MutableStateFlow<Boolean?>(null)
    val showProgress: StateFlow<Boolean?> = _showProgress.asStateFlow()

    fun getTrainRun(trainRunId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showProgress.emit(true)
            _trainRun.emit(getTrainRunUseCase.execute(trainRunId))
        }
    }

    fun getStatuses(trainRun: TrainRun) {
        viewModelScope.launch {
            val listStatuses = mutableListOf<Status>()
            if (drivers.value.isNotEmpty()) {
                drivers.value.forEach {
                    if (it != null) {
                        val el = getStatusCompletionTrainRunUseCase.execute(it.id, trainRun.startTime)
                        if (el != null) listStatuses.add(el.fromDTO)
                    }
                }
                _statuses.emit(listStatuses)
            }
        }
    }

    fun getDrivers(trainRun: TrainRun) =
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) {
                findDriverBeforeHorizonUseCase.execute(trainRun, CHECK_WEEKENDS)
            })

        }

    fun getSelectionDriverData(trainRun: TrainRun) {
        var isShowProgress = true
        viewModelScope.launch {

            combine(drivers, statuses) { dr, st ->
                val listData = mutableListOf<SelectionDriverItemData>()
                dr.forEach { driver ->
                    val statusLoc = st.filter { it.idDriver == driver!!.id }
                    val currentSt: Status
                    if (statusLoc.isEmpty()) {
                        currentSt = Status(
                            driver!!.id,
                            trainRun.startTime,
                            status = 3,
                            countNight = 0,
                            workedTime = 0,
                            trainRun.id
                        )
                    } else currentSt = statusLoc.first()
                    val restTime = if (currentSt.date == trainRun.startTime) 999 else
                        Math.abs(trainRun.startTime - currentSt.date).toHoursTimeString.toInt()
                    listData.add(
                        SelectionDriverItemData(
                            driverName = driver?.FIO,
                            if (driver?.id != null) driver.id else 0,
                            restTime = restTime,
                            workedTime = currentSt.workedTime.toHoursTimeString
                        )
                    )
                }
                listData.sortBy { it.restTime }
                listData
            }.collect { _dataVisual.emit(withContext(Dispatchers.IO) { it })
                if (isShowProgress) {
                    _showProgress.emit(false)
                    isShowProgress = false
                }}
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