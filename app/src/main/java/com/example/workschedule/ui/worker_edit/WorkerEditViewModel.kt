package com.example.workschedule.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.data.database.DriverDataBase
import com.example.workschedule.data.database.driver.DriverDao
import com.example.workschedule.data.database.driver.DriverEntity
import com.example.workschedule.domain.domainpersonmodel.DomainPathDirectionModel
import com.example.workschedule.ui.worker_edit.listDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkerEditViewModel(val db: DriverDao) : ViewModel() {


    private var _directions = MutableStateFlow<List<DomainPathDirectionModel>>(emptyList())
    val directions: StateFlow<List<DomainPathDirectionModel>> = _directions.asStateFlow()

    fun getDirections() {
        viewModelScope.launch {
            _directions.emit(listDirection)
        }
    }

//    suspend fun createDriver(){
//        db.saveOrChange()
//    }

    suspend fun saveNewDriverFake() {
        db.saveOrChange(DriverEntity(
            1,
            "Ivan",
            "Ivanov",
            "Petrovich",
            0,
            0,
            listOf(1)
        ))

    }
}