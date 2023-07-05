package com.example.workschedule.ui.main.finddriver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectionDriverViewModel(

) : ViewModel() {

    private var _drivers = MutableStateFlow<List<Driver>>(emptyList())
    val driversSelect: StateFlow<List<Driver>> = _drivers.asStateFlow()

    private var _statuses = MutableStateFlow<List<Status>>(emptyList())
    val statuses: StateFlow<List<Status>> = _statuses.asStateFlow()

    fun getSelectionDriverData() {
        viewModelScope.launch {
//            _drivers.emit(withContext(Dispatchers.IO) {})
        }
    }


}