package com.example.workschedule.ui.route_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RouteEditViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is a route edit fragment"
    }
    val text: LiveData<String> = _text
}
