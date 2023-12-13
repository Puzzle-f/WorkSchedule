package com.example.workschedule.domain.usecases.distraction

//execute() == true, если у машиниста не выходной

class CheckDistractionUseCase(
    private val getLastStatusDistractionUseCase: GetLastStatusDistractionUseCase
) {
    suspend fun execute(driverId: Int, dateStart: Long, dateEnd: Long): Boolean {
        val firstStatus = getLastStatusDistractionUseCase
            .execute(driverId, dateStart)
        val lastStatus = getLastStatusDistractionUseCase
            .execute(driverId, dateEnd)
        return (firstStatus == null && lastStatus == null ) ||
                (firstStatus != null && firstStatus == lastStatus && !firstStatus.isDistracted)
    }
}