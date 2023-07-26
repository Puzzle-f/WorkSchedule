package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverAfterHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverBeforeHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.status.DeleteStatusForTrainRunIdUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.trainrun.ClearDriverForTrainRunAfterDateUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteAllTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.GetAllTrainsRunListUseCase
import com.example.workschedule.ui.settings.PLANNING_HORIZON
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toLong
import com.example.workschedule.utils.toTimeString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters

class MainFragmentViewModel(
    private val getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteTrainRunUseCase: DeleteTrainRunUseCase,
    private val deleteAllTrainRunUseCase: DeleteAllTrainRunUseCase,
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val recalculateStatusesForForDriverAfterTimeUseCase: RecalculateStatusesForDriverAfterTimeUseCase,
    private val findDriverBeforeHorizonUseCase: FindDriverBeforeHorizonUseCase,
    private val findDriverAfterHorizonUseCase: FindDriverAfterHorizonUseCase,
    private val clearDriverForTrainRunAfterDateUseCase: ClearDriverForTrainRunAfterDateUseCase,
    private val deleteStatusForTrainRunIdUseCase: DeleteStatusForTrainRunIdUseCase
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

    fun clearDriverForTrainRunAfterDate(date: Long) =
        viewModelScope.launch(Dispatchers.IO) {
//            val horizonDate = LocalDateTime.now().toLong()  + PLANNING_HORIZON
            clearDriverForTrainRunAfterDateUseCase.execute(date).join()
        }

    fun findDriver() =
        viewModelScope.launch(Dispatchers.IO) {
            val clearDriver = clearDriverForTrainRunAfterDate(
//                TODO заглушка для очистки всех поездов с начала текщего месяца. Переделать на горизонт планирования
                LocalDateTime . now ().with(TemporalAdjusters.firstDayOfMonth() ).toLong()
            )
            clearDriver.join()
            delay(100)
            val horizonDate = LocalDateTime.now().toLong() + PLANNING_HORIZON

                trainRunList.value.forEach {
                    if(!it.isEditManually)
                    deleteStatusForTrainRunIdUseCase.execute(it.id).join()
                }


//            trainRunList
//                .collect {
//                        listTrainRun ->
            trainRunList.value.forEach {
                    if (it.driverId == 0) {
                        if (it.startTime <= horizonDate) {
//                    findDriverBeforeHorizonUseCase.execute(it)
                            findDriverAfterHorizonUseCase.execute(it).join()
                        } else {
                            findDriverAfterHorizonUseCase.execute(it).join()
                        }
//                        findDriverAfterHorizonUseCase.execute(it).join()
                        if (it == trainRunList.value.last()) this.cancel()
                    }

                }
//            }
        }


    fun deleteAllTrainRun() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTrainRunUseCase.execute()
        }
    }
}