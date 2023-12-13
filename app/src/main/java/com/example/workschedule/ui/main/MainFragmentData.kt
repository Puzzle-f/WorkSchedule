package com.example.workschedule.ui.main

data class MainFragmentData(
    val id: Int,
    val data: Long,
    val time: String,
    val trainNumber: Int,
    val direction: String,
    var driver: String?,
    val roadTime: String,
    val workTime: String,
    val countNight: Int,
    val isEditManually: Boolean
)