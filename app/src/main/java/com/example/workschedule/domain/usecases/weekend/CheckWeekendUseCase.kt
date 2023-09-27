package com.example.workschedule.domain.usecases.weekend


/**
 * execute() == true, если у машиниста не выходной
 */

class CheckWeekendUseCase(
    private val getLastStatusWeekendUseCase: GetLastStatusWeekendUseCase
) {
    suspend fun execute(driverId: Int, dateStart: Long, dateEnd: Long): Boolean {
        val firstStatus = getLastStatusWeekendUseCase
                            .execute(driverId, dateStart)
        val lastStatus = getLastStatusWeekendUseCase
                            .execute(driverId, dateEnd)
        return (firstStatus == null && lastStatus == null ) ||
            (firstStatus != null && firstStatus == lastStatus && !firstStatus.startWeekend)
    }
}