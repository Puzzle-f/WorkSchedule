//package com.example.workschedule.domain.usecases.trainrun
//
//import com.example.workschedule.domain.DomainRepository
//import com.example.workschedule.domain.models.TrainRun
//
//class AppointDriverUseCase(
//    private val repository: DomainRepository
//) {
//    suspend fun execute() {
//        val drivers = repository.getAllDriversList()
//        val trainRunList = repository.getAllTrainsRunList()
//        trainRunList.forEach { trainRun ->
//            if (trainRun.driverId == 0) {
//                trainRun.driverId =
//                    drivers.filter { driver ->
//                        repository.getDriversByPermission(trainRun.direction).contains(driver.id)
//                    }
//                        .filter { driver ->
//                            repository.getLastStatus(driver.id, trainRun.startTime).status == 3
//                        }
//                        .minByOrNull {
//                            repository.getLastStatus(
//                                it.id,
//                                trainRun.startTime
//                            ).workedTime
//                        }
//                        .let { it?.id ?: 0 }
//            }
//            if(trainRun.driverId!=0){
//                addListStatus()
//            }
//        }
//    }
//
//    suspend fun addListStatus(trainRun: TrainRun){
//        repository
//
//
//    }
//}