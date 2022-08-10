package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.driver.GetAllDriversListUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteAllTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.DeleteTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.GetAllTrainsRunListUseCase
import com.example.workschedule.domain.usecases.trainrun.SaveTrainRunListUseCase
import com.example.workschedule.utils.fillTrainRunListWithDrivers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class MainFragmentViewModel(
    private val getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val saveTrainRunListUseCase: SaveTrainRunListUseCase,
    private val deleteTrainRunUseCase: DeleteTrainRunUseCase,
    private val deleteAllTrainRunUseCase: DeleteAllTrainRunUseCase
) : ViewModel() {

    private var _trainsRunList = MutableStateFlow<List<TrainRun>>(emptyList())
    val trainsRunList: StateFlow<List<TrainRun>> = _trainsRunList.asStateFlow()

    fun getTrainsRunList() {
        viewModelScope.launch {
            val trainRunList = withContext(Dispatchers.IO) { getAllTrainsRunListUseCase.execute() }
            val driverList = withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() }
            trainRunList.filter { it.startTime >= LocalDateTime.now() }.forEach {
                it.driverId = 0
                it.driverName = ""
            }
            withContext(Dispatchers.Default) { trainRunList.fillTrainRunListWithDrivers(driverList) }
            withContext(Dispatchers.IO) { saveTrainRunListUseCase.execute(trainRunList) }
            _trainsRunList.emit(trainRunList)
        }
    }

    fun deleteTrainRun(trainRunId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrainRunUseCase.execute(trainRunId)
        }
    }

    fun deleteAllTrainRun() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllTrainRunUseCase.execute()
        }
    }
}