package com.example.workschedule.ui.trains

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workschedule.data.entities.Train
import com.example.workschedule.utils.trainList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainsViewModel : ViewModel() {

    private var _trains = MutableStateFlow<List<Train>>(emptyList())
    val trains: StateFlow<List<Train>> = _trains.asStateFlow()

    suspend fun getTrains() {
        _trains.emit(trainList)
    }
}