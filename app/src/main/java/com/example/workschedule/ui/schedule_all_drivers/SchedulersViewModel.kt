package com.example.workschedule.ui.schedule_all_drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.GetAllDriversListUseCase
import com.example.workschedule.domain.GetTrainRunListForDriverUseCase
import com.example.workschedule.ui.schedule_all_drivers.model.HorizontalRVModel
import com.example.workschedule.ui.schedule_all_drivers.model.VerticalRVModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SchedulersViewModel(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getTrainRunListForDriverUseCase: GetTrainRunListForDriverUseCase
) : ViewModel() {

    private var _data = MutableStateFlow<List<VerticalRVModel>>(emptyList())
    val data: StateFlow<List<VerticalRVModel>> = _data.asStateFlow()

    fun getVerticalRVModel() {
        viewModelScope.launch {
            val drivers = withContext(Dispatchers.IO) { getAllDriversListUseCase.execute() }
            val dataList = mutableListOf<VerticalRVModel>()
            drivers.forEach { driver ->
                dataList.add(
                    VerticalRVModel(
                        driver, trainRunsToHorizontalRVModel(driver.id)
                    )
                )
            }
            _data.emit(dataList)
        }
    }

    private suspend fun trainRunsToHorizontalRVModel(driverId: Int): List<HorizontalRVModel> {
        val listHorizontalRVModel = mutableListOf<HorizontalRVModel>()
        val driverTrainRuns =
            (withContext(Dispatchers.IO) { getTrainRunListForDriverUseCase.execute(driverId) })
        driverTrainRuns.forEach {
            listHorizontalRVModel.add(
                HorizontalRVModel(
                    it.startTime.toString(),
                    it.trainNumber.toString()
                )
            )
        }
        return listHorizontalRVModel
    }
}

