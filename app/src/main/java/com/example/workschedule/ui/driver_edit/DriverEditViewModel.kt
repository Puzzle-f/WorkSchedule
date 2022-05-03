package com.example.workschedule.ui.driver_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DriverEditViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is driver edit fragment"
    }
    val text: LiveData<String> = _text
}
