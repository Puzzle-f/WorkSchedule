package com.example.workschedule.ui.train_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.usecases.train.GetTrainUseCase
import com.example.workschedule.domain.usecases.train.SaveTrainUseCase
import com.example.workschedule.domain.models.Train
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainEditViewModel(
    private val getTrainUseCase: GetTrainUseCase,
    private val saveTrainUseCase: SaveTrainUseCase
) : ViewModel() {

    private var _train = MutableStateFlow<Train?>(null)
    val train: StateFlow<Train?> = _train.asStateFlow()

    fun getTrain(number: Int) {
        viewModelScope.launch {
            _train.emit(withContext(Dispatchers.IO) { getTrainUseCase.execute(number) })
        }
    }

    fun saveTrain(train: Train) {
        viewModelScope.launch(Dispatchers.IO) {
            saveTrainUseCase.execute(train)
        }
    }
}