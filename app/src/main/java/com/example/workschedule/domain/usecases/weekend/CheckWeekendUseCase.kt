package com.example.workschedule.domain.usecases.weekend

class CheckWeekendUseCase(
    private val getLastStatusWeekendUseCase: GetLastStatusWeekendUseCase
) {
    suspend fun execute(driverId: Int, dateStart: Long, dateEnd: Long): Boolean {
        val firstStatus = getLastStatusWeekendUseCase
                            .execute(driverId, dateStart)
        val lastStatus = getLastStatusWeekendUseCase
                            .execute(driverId, dateEnd)
        return if (firstStatus==null && lastStatus ==null) true
        else firstStatus==lastStatus
    }
}