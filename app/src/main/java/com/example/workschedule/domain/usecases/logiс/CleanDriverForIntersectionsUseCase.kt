package com.example.workschedule.domain.usecases.logiс

import android.util.Log
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.status.GetStatusesForDriverBetweenDateUseCase
import com.example.workschedule.domain.usecases.status.GetStatusesForTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.ClearDriverForTrainRunBetweenDateUseCase
import com.example.workschedule.domain.usecases.trainrun.ClearDriverForAllTrainRunUseCase
import com.example.workschedule.domain.usecases.trainrun.ClearDriverForTrainRunUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CleanDriverForIntersectionsUseCase(
    private val getStatusesForTrainRunUseCase: GetStatusesForTrainRunUseCase,
    private val getStatusesForDriverBetweenDateUseCase: GetStatusesForDriverBetweenDateUseCase,
    private val clearDriverForTrainRunUseCase: ClearDriverForTrainRunUseCase
) {

    suspend fun execute(trainRun: TrainRun, driverId: Int) {
        coroutineScope {
            launch(Dispatchers.IO){
                val rangeStatuses = getStatusesForTrainRunUseCase.execute(trainRun.id)
                Log.e("", "!!! rangeStatuses = $rangeStatuses")
                if (rangeStatuses.isNotEmpty()){
                    getStatusesForDriverBetweenDateUseCase.execute(
                        driverId = driverId,
                        dateStart = trainRun.startTime,
                        dateEnd = rangeStatuses.maxOf { it.date })
                    ?.forEach { st ->
                        st.let {
                            if (st.idBlock != trainRun.id)
                                clearDriverForTrainRunUseCase.execute(it.idBlock!!)
                        }
                    }
                }
            }
        }
    }
}