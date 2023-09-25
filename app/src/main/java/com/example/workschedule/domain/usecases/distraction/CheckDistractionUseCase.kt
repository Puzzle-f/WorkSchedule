package com.example.workschedule.domain.usecases.distraction

class CheckDistractionUseCase(
    private val getLastStatusDistractionUseCase: GetLastStatusDistractionUseCase
) {
    suspend fun execute(driverId: Int, dateStart: Long, dateEnd: Long): Boolean {
        val firstStatus = getLastStatusDistractionUseCase
            .execute(driverId, dateStart)
        val lastStatus = getLastStatusDistractionUseCase
            .execute(driverId, dateEnd)
        return if (firstStatus == null && lastStatus == null) true
        else firstStatus == lastStatus && !firstStatus!!.isDistracted
    }
}