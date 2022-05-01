package com.example.workschedule.ui.main

data class TestModelForMainAdapter(
    val date: String = "${(1..31).random()}.${(1..12).random()}.2022",
    var time: String = "${(1..24).random()}:${(1..60).random()}",
    val train: String = "${(100..999).random()} Moscow",
    val travelTimeTo: String = "${(0..24).random()}:${(0..60).random()}",
    val restTime: String = "${(1..24).random()}",
    val travelFrom: String = "${(0..24).random()}:${(0..60).random()}",
)