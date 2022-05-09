package com.example.workschedule.ui.driver_edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.data.database.driver.DriverDao
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.domain.models.direction.DomainPathDirectionModel
import com.example.workschedule.ui.worker_edit.listDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DriverEditViewModel(db: DriverDao) : ViewModel() {
    private var _directions = MutableStateFlow<List<DomainPathDirectionModel>>(emptyList())
    val directions: StateFlow<List<DomainPathDirectionModel>> = _directions.asStateFlow()

    private val _text = MutableLiveData<String>().apply {
        value = "This is driver edit fragment"
    }

    fun getDirections() {
        viewModelScope.launch {
            _directions.emit(listDirection)
        }
    }

    suspend fun createDriver(
        id: Int,
        name: String,
        surName: String,
        patronymic: String,
        workedTime: Long,
        totalTime: Long,
        direction: List<Int> = listOf(1)
    ) = db.saveOrChange(
        DriverEntity(
            id, name, surName, patronymic, workedTime, totalTime, direction
        )
    )
}
