package com.example.workschedule.ui.weekend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.WeekendStatus
import com.example.workschedule.domain.usecases.weekend.GetWeekendsUseCase
import com.example.workschedule.domain.usecases.weekend.SaveWeekendUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeekendViewModel(
    private val getWeekends: GetWeekendsUseCase,
    private val saveWeekends: SaveWeekendUseCase
): ViewModel() {

    private var _weekends = MutableStateFlow<List <WeekendStatus>>(emptyList())
    val weekends: StateFlow<List<WeekendStatus>> = _weekends.asStateFlow()

    fun getWeekends(idDriver: Int, date: Long){
        viewModelScope.launch {
            _weekends.emit(withContext(Dispatchers.IO){
                getWeekends.execute(idDriver, date)
            })
        }
    }

    fun saveWeekend(idDriver: Int, ){
//        saveWeekends.execute()
    }





}