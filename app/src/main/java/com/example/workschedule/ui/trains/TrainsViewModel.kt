package com.example.workschedule.ui.trains

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.usecases.train.DeleteTrainUseCase
import com.example.workschedule.domain.usecases.train.GetAllTrainsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainsViewModel(
    private val getAllTrainsListUseCase: GetAllTrainsListUseCase,
    private val deleteTrainUseCase: DeleteTrainUseCase
) : ViewModel() {

    private var _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(withContext(Dispatchers.IO) { getAllTrainsListUseCase.execute() })
        }
    }

    fun deleteTrain(trainId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrainUseCase.execute(trainId)
        }
    }
}