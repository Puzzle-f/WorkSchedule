package com.example.workschedule.ui.trains

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.DomainTrainModel
import com.example.workschedule.utils.trainList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainsViewModel : ViewModel() {

    private var _trains = MutableStateFlow<List<DomainTrainModel>>(emptyList())
    val trains: StateFlow<List<DomainTrainModel>> = _trains.asStateFlow()

    fun getTrains() {
        viewModelScope.launch {
            _trains.emit(trainList)
        }
    }
}