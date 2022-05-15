package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.DeleteTrainRunUseCase
import com.example.workschedule.domain.GetAllDriversListUseCase
import com.example.workschedule.domain.GetAllTrainsRunListUseCase
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.utils.fillTrainRunListWithDrivers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainFragmentViewModel(
    private val getAllTrainsRunListUseCase: GetAllTrainsRunListUseCase,
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val deleteTrainRunUseCase: DeleteTrainRunUseCase
) : ViewModel() {

    private var _trainsRunList = MutableStateFlow<List<TrainRun>>(emptyList())
    val trainsRunList: StateFlow<List<TrainRun>> = _trainsRunList.asStateFlow()

    fun getTrainsRunList() {
        viewModelScope.launch {
            val trainRunList = getAllTrainsRunListUseCase.execute()
                .fillTrainRunListWithDrivers(getAllDriversListUseCase.execute())
            _trainsRunList.emit(
                trainRunList
            )
        }
    }

    fun deleteTrainRun(trainRunId: Int) {
        viewModelScope.launch {
            deleteTrainRunUseCase.execute(trainRunId)
        }
    }
}