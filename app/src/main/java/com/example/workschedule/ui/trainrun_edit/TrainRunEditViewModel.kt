package com.example.workschedule.ui.trainrun_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.models.TrainPeriodicity
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.train.GetAllTrainsListUseCase
import com.example.workschedule.domain.usecases.trainrun.GetTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunListUseCase
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunUseCase
import com.example.workschedule.utils.changeDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainRunEditViewModel(
    private val getTrainRunUseCase: GetTrainRunUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getAllTrainsListUseCase: GetAllTrainsListUseCase,
    private val saveTrainRunUseCase: SaveTrainRunUseCase,
    private val saveTrainRunListUseCase: SaveTrainRunListUseCase
) : ViewModel() {
    private var _trainRun = MutableStateFlow<TrainRun?>(null)
    val trainRun: StateFlow<TrainRun?> = _trainRun.asStateFlow()
    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()
    private var _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun getTrainRun(trainRunId: Int) {
        viewModelScope.launch {
            _trainRun.emit(withContext(Dispatchers.IO) { getTrainRunUseCase.execute(trainRunId) })
        }
    }

    fun getDrivers() {
        viewModelScope.launch {
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
        }
    }

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(withContext(Dispatchers.IO) { getAllTrainsListUseCase.execute() })
        }
    }

    fun saveTrainRun(trainRun: TrainRun) {
        viewModelScope.launch(Dispatchers.IO) {
            val daysInMonth = trainRun.startTime.toLocalDate().lengthOfMonth()
            if (trainRun.id == 0) {
                when (trainRun.trainPeriodicity) {
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
                saveTrainRunUseCase.execute(trainRun)
            }
        }
    }
}
