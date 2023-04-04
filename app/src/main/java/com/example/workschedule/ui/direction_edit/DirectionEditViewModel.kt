package com.example.workschedule.ui.direction_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workschedule.domain.usecases.train.GetDirectionUseCase
import com.example.workschedule.domain.usecases.train.SaveDirectionUseCase
import com.example.workschedule.domain.models.Direction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DirectionEditViewModel(
    private val getDirectionUseCase: GetDirectionUseCase,
    private val saveDirectionUseCase: SaveDirectionUseCase
) : ViewModel() {

    private var _direction = MutableStateFlow<Direction?>(null)
    val direction: StateFlow<Direction?> = _direction.asStateFlow()

    fun getDirection(number: Int) {
        viewModelScope.launch {
            _direction.emit(withContext(Dispatchers.IO) { getDirectionUseCase.execute(number) })
        }
    }

    fun saveDirection(direction: Direction) {
        viewModelScope.launch(Dispatchers.IO) {
            saveDirectionUseCase.execute(direction)
        }
    }
}