package com.example.workschedule.ui.direction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Direction
import com.example.workschedule.domain.usecases.train.DeleteDirectionUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DirectionsViewModel(
    private val getAllDirectionsListUseCase: GetAllDirectionsListUseCase,
    private val deleteDirectionUseCase: DeleteDirectionUseCase
) : ViewModel() {

    private var _trains = MutableStateFlow<List<Direction>>(emptyList())
    val trains: StateFlow<List<Direction>> = _trains.asStateFlow()

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(withContext(Dispatchers.IO) { getAllDirectionsListUseCase.execute() })
        }
    }

    fun deleteTrain(trainId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteDirectionUseCase.execute(trainId)
        }
    }
}