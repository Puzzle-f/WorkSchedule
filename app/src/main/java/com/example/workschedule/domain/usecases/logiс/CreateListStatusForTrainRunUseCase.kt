package com.example.workschedule.domain.usecases.logiс

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.usecases.status.CreateStatusUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.ui.settings.MIN_REST
import com.example.workschedule.ui.settings.nightPeriod
import com.example.workschedule.utils.toDTO
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CreateListStatusForTrainRunUseCase(
private val getLastStatusUseCase: GetLastStatusUseCase,
private val createStatusUseCase: CreateStatusUseCase
) {
    suspend fun execute(trainRun: TrainRun) =
        coroutineScope{
            launch(Dispatchers.IO) {
                if (trainRun.driverId != 0) {
                    val lastStatus: StatusEntity? = getLastStatusUseCase.execute(
                        trainRun.driverId,
                        trainRun.startTime
                    )
//      Создать статус 1 "в поездке"
                    val statusOnTrip = Status(
                        trainRun.driverId,
                        trainRun.startTime,
                        1,
                        lastStatus?.countNight ?: 0,
                        lastStatus?.workedTime ?: 0,
                        trainRun.id
                    ).toDTO
                    createStatusUseCase.execute(statusOnTrip)

//      Создать статус 2 "отдых после поездки"
                    val statusAfterTrip = Status(
                        trainRun.driverId,
                        trainRun.startTime + trainRun.travelTime,
                        2,
                        trainRun.countNight,
                        lastStatus?.workedTime?.plus(trainRun.workTime) ?: trainRun.workTime,
                        trainRun.id
                    ).toDTO
                    createStatusUseCase.execute(statusAfterTrip)

//            Создать статус 3 "в ожидании работы"

//            Если окончание работы больше этого времени, то после минимального отдыха (16ч) у тчм будет 0 ночей
                    val controlTime = statusAfterTrip.date.toLocalDateTime()
                        .plusDays(1)
                        .toLocalDate()
                        .atStartOfDay()
                        .plusHours(nightPeriod.endInclusive.hour.toLong())
                        .minusHours(MIN_REST)

                    val countNight =
                        if (statusAfterTrip.date.toLocalDateTime() > controlTime) 0 else statusAfterTrip.countNight
                    val statusWaitingForWork = Status(
                        trainRun.driverId,
                        trainRun.startTime + trainRun.travelTime + MIN_REST * 3600000L,
                        3,
                        countNight = countNight,
                        statusAfterTrip.workedTime,
                        trainRun.id
                    ).toDTO
                    createStatusUseCase.execute(statusWaitingForWork)

//            Создать статус 3 "в ожидании работы" с количеством ночей = 0

                    if (statusWaitingForWork.countNight != 0 && statusAfterTrip.date.toLocalDateTime()
                            .toLocalDate() ==
                        statusWaitingForWork.date.toLocalDateTime()
                            .toLocalDate()
                    ) {
                        val statusWaitingForWorkCountNightIsZero = Status(
                            trainRun.driverId,
                            date = statusWaitingForWork.date.toLocalDateTime().toLocalDate().plusDays(1)
                                .atStartOfDay().plusHours(
                                    nightPeriod.endInclusive.hour.toLong()
                                ).toLong(),
                            3,
                            0,
                            statusAfterTrip.workedTime,
                            trainRun.id
                        ).toDTO
                        createStatusUseCase.execute(statusWaitingForWorkCountNightIsZero)
                    } else if (statusWaitingForWork.countNight != 0 &&
                        statusAfterTrip.date.toLocalDateTime().toLocalDate() !=
                        statusWaitingForWork.date.toLocalDateTime().toLocalDate() &&
                        statusWaitingForWork.date.toLocalDateTime() <
                        statusWaitingForWork.date.toLocalDateTime().toLocalDate()
                            .atStartOfDay().plusHours(
                                nightPeriod.endInclusive
                                    .hour.toLong().toLocalDateTime().toLong()
                            )
                    ) {
                        val statusWaitingForWorkCountNightIsZero = Status(
                            trainRun.driverId,
                            date = statusWaitingForWork.date.toLocalDateTime().toLocalDate()
                                .atStartOfDay().plusHours(
                                    nightPeriod.endInclusive
                                        .hour.toLong().toLocalDateTime().toLong()
                                ).toLong(),
                            3,
                            0,
                            statusAfterTrip.workedTime,
                            trainRun.id
                        ).toDTO
                        createStatusUseCase.execute(statusWaitingForWorkCountNightIsZero)
                    }
                }
            }
        }

}