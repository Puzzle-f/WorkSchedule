package com.example.workschedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.usecases.trainrun.DeleteTrainRunUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val deleteTrainRunUseCase: DeleteTrainRunUseCase) : ViewModel() {

    fun clearTrainRun() {
        viewModelScope.launch {
            deleteTrainRunUseCase.executeAll()
        }
    }
}