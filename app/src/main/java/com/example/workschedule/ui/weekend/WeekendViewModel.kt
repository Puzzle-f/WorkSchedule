package com.example.workschedule.ui.weekend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Weekend
import com.example.workschedule.domain.usecases.weekend.DeleteAllWeekendsForDriverUseCase
import com.example.workschedule.domain.usecases.weekend.DeleteWeekendUseCase
import com.example.workschedule.domain.usecases.weekend.GetWeekendsUseCase
import com.example.workschedule.domain.usecases.weekend.SaveWeekendUseCase
import com.example.workschedule.utils.toLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WeekendViewModel(
    private val getWeekends: GetWeekendsUseCase,
    private val saveWeekendsUseCase: SaveWeekendUseCase,
    private val deleteWeekendUseCase: DeleteWeekendUseCase,
    private val deleteAllWeekendsForDriverUseCase: DeleteAllWeekendsForDriverUseCase
) : ViewModel() {

    private var _weekends = MutableStateFlow<List<Weekend>>(emptyList())
    val weekends: StateFlow<List<Weekend>> = _weekends.asStateFlow()

    fun getWeekends(idDriver: Int) {
        viewModelScope.launch {
            getWeekends.execute(idDriver).collect{ listWeekend ->
                _weekends.emit(withContext(Dispatchers.IO) { listWeekend})
            }
        }
    }

    fun saveWeekend(idDriver: Int, date: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            val startWeekendTime = date.atStartOfDay().toLong()
            val endWeekendTime = date.atTime(23, 59).toLong()
            saveWeekendsUseCase.execute(Weekend(idDriver, startWeekendTime, true))
            saveWeekendsUseCase.execute(Weekend(idDriver, endWeekendTime, false))
        }
    }

    fun deleteWeekend(idDriver: Int, date: LocalDate){
        viewModelScope.launch(Dispatchers.IO) {
            val startWeekendTime = date.atStartOfDay().toLong()
            val endWeekendTime = date.atTime(23, 59, 59).toLong()
            deleteWeekendUseCase.execute(idDriver, startWeekendTime, endWeekendTime)
        }
    }

    fun deleteAllWeekendsForDriver(idDriver: Int){
        viewModelScope.launch (Dispatchers.IO) {
            deleteAllWeekendsForDriverUseCase.execute(idDriver)
        }
    }

}