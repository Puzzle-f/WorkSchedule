package com.example.workschedule.ui.trainrun_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunListUseCase
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.UpdateTrainRunUseCase
import com.example.workschedule.utils.changeDay
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toTimeString
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
    private val updateTrainRunUseCase: UpdateTrainRunUseCase
) : ViewModel() {
    private var _trainRun = MutableStateFlow<TrainRun?>(null)
    val trainRun: StateFlow<TrainRun?> = _trainRun.asStateFlow()
    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()
    private var _directions = MutableStateFlow<List<Direction>>(emptyList())
    val directions: StateFlow<List<Direction>> = _directions.asStateFlow()

    private var _trainRunEditVisual = MutableStateFlow<TrainRunEditVisual?>(null)
    val trainRunEditVisual: StateFlow<TrainRunEditVisual?> = _trainRunEditVisual.asStateFlow()

    fun getTrainRunData(trainRunId: Int) {
        viewModelScope.launch {
            _trainRun.emit(withContext(Dispatchers.IO) { getTrainRunUseCase.execute(trainRunId) })
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
            if (trainRunId != 0) {
                combine(trainRun, drivers, directions) { trainRun, drivers, directions ->
                    val driver = if(trainRun!!.driverId==0) "" else
                        drivers.first { trainRun.driverId == it.id }.surname
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

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
        }
    }

    fun getDirections() {
        viewModelScope.launch {
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
        }
    }

    fun saveTrainRun(trainRun: TrainRun) {
        viewModelScope.launch(Dispatchers.IO) {
            val daysInMonth = trainRun.startTime.toLocalDateTime().toLocalDate().lengthOfMonth()
            if (trainRun.id == 0) {
                when (trainRun.periodicity) {
                    TrainPeriodicity.SINGLE -> saveTrainRunUseCase.execute(trainRun)
                    TrainPeriodicity.ON_ODD -> saveTrainRunListUseCase.execute(
                        (1..daysInMonth step 2).map { trainRun.changeDay(it) }
                    )
                    TrainPeriodicity.ON_EVEN -> saveTrainRunListUseCase.execute(
                        (2..daysInMonth step 2).map { trainRun.changeDay(it) }
                    )
                    TrainPeriodicity.DAILY -> saveTrainRunListUseCase.execute(
                        (1..daysInMonth).map { trainRun.changeDay(it) }
                    )
                }
            } else {
                updateTrainRunUseCase.execute(trainRun)
            }
        }
    }
}
