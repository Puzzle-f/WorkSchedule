package com.example.workschedule.domain.usecases.driver

import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase

class GetAvailableDriversUseCase(
    private val getAllDriversListUseCase: GetAllDriversListUseCase,
    private val getLastStatusUseCase: GetLastStatusUseCase,
) {
    suspend fun execute(date: Long, countNight: Int) =
        getAllDriversListUseCase.execute().filter {
            val lastStatus = getLastStatusUseCase.execute(it.id, date)
            lastStatus?.status == 3 && lastStatus.countNight <= countNight
        }
}