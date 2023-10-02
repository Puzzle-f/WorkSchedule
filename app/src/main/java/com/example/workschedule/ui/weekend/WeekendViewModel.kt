package com.example.workschedule.ui.weekend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Distraction
import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.Weekend
import com.example.workschedule.domain.usecases.distraction.*
import com.example.workschedule.domain.usecases.driver.GetDriverUseCase
import com.example.workschedule.domain.usecases.weekend.DeleteAllWeekendsForDriverUseCase
import com.example.workschedule.domain.usecases.weekend.DeleteWeekendUseCase
import com.example.workschedule.domain.usecases.weekend.GetWeekendsUseCase
import com.example.workschedule.domain.usecases.weekend.SaveWeekendUseCase
import com.example.workschedule.utils.toLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class WeekendViewModel(
    private val getWeekends: GetWeekendsUseCase,
    private val saveWeekendsUseCase: SaveWeekendUseCase,
    private val deleteWeekendUseCase: DeleteWeekendUseCase,
    private val deleteAllWeekendsForDriverUseCase: DeleteAllWeekendsForDriverUseCase,
    private val getDistractionUseCase: GetDistractionUseCase,
    private val saveDistractionUseCase: SaveDistractionUseCase,
    private val deleteDistractionUseCase: DeleteDistractionUseCase,
    private val deleteAllDistractionsForDriverUseCase: DeleteAllDistractionsForDriverUseCase,
    private val getLastStatusDistractionUseCase: GetLastStatusDistractionUseCase,
    private val getDriverUseCase: GetDriverUseCase
) : ViewModel() {

    private var _driver = MutableStateFlow<Driver?>(null)
    val driver: StateFlow<Driver?> = _driver.asStateFlow()

    private var _weekends = MutableStateFlow<List<Weekend>>(emptyList())
    val weekends: StateFlow<List<Weekend>> = _weekends.asStateFlow()

    private var _distractions = MutableStateFlow<List<Distraction>>(emptyList())
    val distractions: StateFlow<List<Distraction>> = _distractions.asStateFlow()

    fun getWeekends(idDriver: Int) {
        viewModelScope.launch {
            getWeekends.execute(idDriver).collect{ listWeekend ->
                _weekends.emit(withContext(Dispatchers.IO) { listWeekend})
            }
        }
    }

    fun getDistractions(idDriver: Int){
        viewModelScope.launch {
            getDistractionUseCase.execute(idDriver).collect{ listDistraction ->
                _distractions.emit(withContext(Dispatchers.IO) { listDistraction})
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

    fun saveDistraction(idDriver: Int, date: LocalDate, isDistractionStart: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val startDistractionTime = date.atStartOfDay().toLong()
            saveDistractionUseCase.execute(Distraction(idDriver, startDistractionTime, isDistractionStart))
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

    fun deleteDistraction(idDriver: Int, date: LocalDate){
        viewModelScope.launch(Dispatchers.IO) {
            val startWeekendTime = date.atStartOfDay().toLong()
            val endWeekendTime = date.atTime(23, 59, 59).toLong()
            deleteDistractionUseCase.execute(idDriver, startWeekendTime, endWeekendTime)
        }
    }

    fun deleteAllDistractionForDriver(idDriver: Int){
        viewModelScope.launch (Dispatchers.IO) {
            deleteAllDistractionsForDriverUseCase.execute(idDriver)
        }
    }

    suspend fun getLastDistractionStatus(idDriver: Int, date: Long)=
//        viewModelScope.launch (Dispatchers.IO) {
            getLastStatusDistractionUseCase.execute(idDriver, date)
//        }

    fun getDriver(driverId: Int){
        viewModelScope.launch (Dispatchers.IO){
            _driver.emit(withContext(Dispatchers.IO) { getDriverUseCase.execute(driverId)})
        }
    }



}