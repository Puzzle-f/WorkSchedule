package com.example.workschedule.ui.train_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.models.Train
import com.example.workschedule.domain.trainList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainEditViewModel : ViewModel() {

    private var _train = MutableStateFlow<Train?>(null)
    val train: StateFlow<Train?> = _train.asStateFlow()

    fun getTrain(number: Int) {
        viewModelScope.launch {
            _train.emit(trainList[number])
        }
    }

    fun saveTrain(train: Train) {
        viewModelScope.launch {
            //todo Вызов UseCase-а с передачей ему поезда в качестве параметров для записи его в БД
        }
    }
}