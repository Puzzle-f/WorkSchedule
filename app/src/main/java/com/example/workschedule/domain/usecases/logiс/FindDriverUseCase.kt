//package com.example.workschedule.domain.usecases.logiс
//
//import com.example.workschedule.domain.models.TrainRun
//import com.example.workschedule.ui.settings.PLANNING_HORIZON
//import com.example.workschedule.utils.toLong
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.launch
//import java.time.LocalDate
//import java.time.LocalDateTime
//
//class FindDriverUseCase(
//    private val findDriverBeforeHorizonUseCase: FindDriverBeforeHorizonUseCase,
//    private val findDriverAfterHorizonUseCase: FindDriverAfterHorizonUseCase
//) {
//
//    suspend fun execute(trainRun: TrainRun) =
//        coroutineScope {
//            val currentDate = LocalDateTime.now().toLong()
//            launch (Dispatchers.IO){
//                if(trainRun.startTime <= currentDate + PLANNING_HORIZON){
//                    findDriverBeforeHorizonUseCase.execute(trainRun)
//                } else {
//
//                    findDriverAfterHorizonUseCase.execute(trainRun)
//
//                }
//            }
//        }
//
//}