package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.distraction.CheckDistractionUseCase
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.driver.GetDriverUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverAfterHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.status.DeleteStatusForTrainRunIdUseCase
import com.example.workschedule.domain.usecases.status.GetStatusesForTrainRunUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.domain.usecases.weekend.CheckWeekendUseCase
import com.example.workschedule.ui.settings.CHECK_WEEKENDS
import com.example.workschedule.ui.settings.PLANNING_HORIZON
import com.example.workschedule.ui.settings.PLANNING_HORIZON_COMMON
import com.example.workschedule.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MainFragmentViewModel(
    private val getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteTrainRunUseCase: DeleteTrainRunUseCase,
    private val deleteAllTrainRunUseCase: DeleteAllTrainRunUseCase,
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val findDriverAfterHorizonUseCase: FindDriverAfterHorizonUseCase,
    private val clearDriverForTrainRunAfterDateUseCase: ClearDriverForTrainRunAfterDateUseCase,
    private val deleteStatusForTrainRunIdUseCase: DeleteStatusForTrainRunIdUseCase,
    private val checkWeekendUseCase: CheckWeekendUseCase,
    private val clearDriverForTrainRunUseCase: ClearDriverForTrainRunUseCase,
    private val getStatusesForTrainRunUseCase: GetStatusesForTrainRunUseCase,
    private val checkDistractionUseCase: CheckDistractionUseCase,
    private val getDriverUseCase: GetDriverUseCase
) : ViewModel() {

    private var _trainRunList = MutableStateFlow<List<TrainRun>>(emptyList())
    val trainRunList: StateFlow<List<TrainRun>> = _trainRunList.asStateFlow()

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val drivers: StateFlow<List<Driver>> = _drivers.asStateFlow()


    private var _directions = MutableStateFlow<List<Direction>>(emptyList())
    val directions: StateFlow<List<Direction>> = _directions.asStateFlow()

    private var _data = MutableStateFlow<List<MainFragmentData>>(emptyList())
    val data: StateFlow<List<MainFragmentData>> = _data.asStateFlow()

    private var _borderHorizon = MutableStateFlow<MainFragmentData?>(null)
    val borderHorizon: StateFlow<MainFragmentData?> = _borderHorizon.asStateFlow()

    private var _showProgress = MutableStateFlow<Boolean?>(null)
    val showProgress: StateFlow<Boolean?> = _showProgress.asStateFlow()

    private suspend fun getAllTrainRun() {
        viewModelScope.launch {
            getAllTrainsRunListUseCase.execute().collect { trains ->
                _trainRunList.emit(withContext(Dispatchers.IO) { trains })
            }
        }
    }

    fun getMainFragmentData() {
        var findBorder = true
        var showProgress = true
        viewModelScope.launch {
            _showProgress.emit(true)
            getAllTrainRun()
            _drivers.emit(withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() })
            _directions.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
            combine(trainRunList, drivers, directions) { trainRunIt, driversIt, directionsIt ->
                val listData = mutableListOf<MainFragmentData>()

                trainRunIt.forEach { trainRunThis ->
                    var driverName = "-"
                    if (trainRunThis.driverId != 0) {
                        if (driversIt.any { it.id == trainRunThis.driverId }) {
                            driverName = driversIt.first { it.id == trainRunThis.driverId }.FIO
                        } else if (getDriverUseCase.execute(trainRunThis.driverId) != null) {
                            driverName = getDriverUseCase.execute(trainRunThis.driverId)!!.FIO
                        }
                    }

                    val dataElement = MainFragmentData(
                        trainRunThis.id,
                        trainRunThis.startTime,
                        trainRunThis.startTime.toLocalDateTime().toLocalTime().toString(),
                        trainRunThis.number.toInt(),
                        directionsIt.first { it.id == trainRunThis.direction }.name,
                        driverName,
                        trainRunThis.travelTime.toTimeString,
                        trainRunThis.workTime.toTimeString,
                        trainRunThis.countNight,
                        trainRunThis.isEditManually
                    )
                    listData.add(dataElement)
                    if (findBorder) {
                        if (trainRunThis.startTime >= LocalDateTime.now()
                                .toLong() + PLANNING_HORIZON
                        ) {
                            _borderHorizon.emit(dataElement)
                            findBorder = false
                        }
                    }
                }
                listData
            }.collect {
                _data.emit(withContext(Dispatchers.IO) { it })
                if (showProgress) {
                    _showProgress.emit(false)
                    showProgress = false
                }
            }
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

    private fun clearDriverForTrainRunAfterDate(date: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            clearDriverForTrainRunAfterDateUseCase.execute(date).join()
        }

    fun findDriverAfterHorizon() =
        viewModelScope.launch(Dispatchers.IO) {
            _showProgress.emit(true)
            val horizonDate = LocalDateTime.now().toLong() + PLANNING_HORIZON
            val horizonDateCommon = LocalDateTime.now().toLong() + PLANNING_HORIZON_COMMON
            clearDriverForTrainRunAfterDate(horizonDate).join()
            trainRunList.value.forEach {
                if (!it.isEditManually && it.startTime > horizonDate)
                    deleteStatusForTrainRunIdUseCase.execute(it.id).join()
            }
            trainRunList.value
                .mixEvenAndOdd()
                .forEach {
                    if (it.driverId == 0) {
                        if (it.startTime in (horizonDate + 1)..horizonDateCommon)
                            findDriverAfterHorizonUseCase.execute(it, CHECK_WEEKENDS).join()
                    }
                }
            _showProgress.emit(false)
        }

    fun deleteAllTrainRun() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTrainRunUseCase.execute()
        }
    }

    fun checkWeekendAllTrainRun() {
        viewModelScope.launch(Dispatchers.IO) {
            trainRunList.value
                .forEach { it ->
                    val statusesTrainRun = getStatusesForTrainRunUseCase.execute(it.id)
                        .filter { it.status == 1 || it.status == 2 }
                    if ((statusesTrainRun.isNotEmpty()
                                && !checkWeekendUseCase.execute(
                            it.driverId,
                            statusesTrainRun.first().date,
                            statusesTrainRun.last().date
                        )
                                )
                        || (statusesTrainRun.isNotEmpty() &&
                                !checkDistractionUseCase.execute(
                                    it.driverId,
                                    statusesTrainRun.first().date,
                                    statusesTrainRun.last().date
                                ))
                    ) {
                        clearDriverForTrainRunUseCase.execute(it.id)
                        recalculateStatusesForForDriverAfterTimeUseCase.execute(
                            it.driverId,
                            it.startTime
                        )
                    }
                }
        }
    }
}