package com.example.workschedule.ui.trainrun_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainRunEditViewModel(
    private val getTrainRunUseCase: GetTrainRunUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val saveTrainRunUseCase: SaveTrainRunUseCase,
    private val saveTrainRunListUseCase: SaveTrainRunListUseCase,
    private val updateTrainRunUseCase: UpdateTrainRunUseCase,
    private val getTrainRunByNumberAndStartTime: GetTrainRunByNumberAndStartTimeUseCase,
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase
) : ViewModel() {

    private var _trainRun = MutableStateFlow<TrainRun?>(null)
    val trainRun: StateFlow<TrainRun?> = _trainRun.asStateFlow()
    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()
    private var _directions = MutableStateFlow<List<Direction>>(emptyList())
    val directions: StateFlow<List<Direction>> = _directions.asStateFlow()

    private var _newTrainRun = MutableStateFlow<TrainRun?>(null)
    val newTrainRun: StateFlow<TrainRun?> = _newTrainRun

    private var _trainRunEditVisual = MutableStateFlow<TrainRunEditVisual?>(null)
    val trainRunEditVisual: StateFlow<TrainRunEditVisual?> = _trainRunEditVisual.asStateFlow()

    fun getTrainRunData(trainRunId: Int) {
        viewModelScope.launch {
            _trainRun.emit(withContext(Dispatchers.IO) { getTrainRunUseCase.execute(trainRunId) })
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
            if (trainRunId != 0) {
                combine(trainRun, drivers, directions) { trainRun, drivers, directions ->
                    val driver = if (trainRun!!.driverId == 0) "" else
                        drivers.first { trainRun.driverId == it.id }.FIO
                    val localData = TrainRunEditVisual(
                        trainRun.number,
                        driver,
                        directions.first { trainRun.direction == it.id }.name,
                        trainRun.startTime,
                        trainRun.travelTime.toTimeString,
                        trainRun.countNight,
                        trainRun.workTime.toTimeString,
                        trainRun.periodicity,
                        trainRun.isEditManually,
                        trainRun.note
                    )
                    localData
                }.collect { _trainRunEditVisual.emit(withContext(Dispatchers.IO) { it }) }
            }
        }
    }

    fun recalculateStatusesForForDriverAfterTimeUseCase(driverId: Int, date: Long)=
        viewModelScope.launch {
            recalculateStatusesForForDriverAfterTimeUseCase.execute(driverId, date).join()
        }


    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
        }
    }

    suspend fun getTrainRunByNumberAndStartTime(number: Int, startTime: Long) =
        _newTrainRun.emit(withContext(Dispatchers.IO) {
            getTrainRunByNumberAndStartTime.execute(
                number,
                startTime
            ).fromDTO
        })

    fun getDirections() {
        viewModelScope.launch {
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
        }
    }

    fun saveTrainRun(trainRun: TrainRun) =
        viewModelScope.launch(Dispatchers.IO) {
            val daysInMonth = trainRun.startTime.toLocalDateTime().toLocalDate().lengthOfMonth()
            if (trainRun.id == 0) {
                when (trainRun.periodicity) {
                    TrainPeriodicity.SINGLE -> saveTrainRunUseCase.execute(trainRun)
                    TrainPeriodicity.ON_ODD -> saveTrainRunListUseCase.execute(
                        (trainRun.startTime.toLocalDateTime().toLocalDate().dayOfMonth..daysInMonth step 2).map { trainRun.changeDay(it) }
                    )
                    TrainPeriodicity.ON_EVEN -> saveTrainRunListUseCase.execute(
                        (trainRun.startTime.toLocalDateTime().toLocalDate().dayOfMonth..daysInMonth step 2).map { trainRun.changeDay(it) }
                    )
                    TrainPeriodicity.DAILY -> saveTrainRunListUseCase.execute(
                        (trainRun.startTime.toLocalDateTime().toLocalDate().dayOfMonth..daysInMonth).map { trainRun.changeDay(it) }
                    )
                }
            } else {
                updateTrainRunUseCase.execute(trainRun)
            }
        }
}
