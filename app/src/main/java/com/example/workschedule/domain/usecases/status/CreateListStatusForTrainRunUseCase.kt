package com.example.workschedule.domain.usecases.status

import com.example.workschedule.data.database.status.StatusEntity
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.models.Status
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.ui.settings.MIN_REST
import com.example.workschedule.ui.settings.nightPeriod
import com.example.workschedule.utils.toDTO
import com.example.workschedule.utils.toLocalDateTime
import com.example.workschedule.utils.toLong

class CreateListStatusForTrainRunUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(trainRun: TrainRun) {
        if (trainRun.driverId != 0) {
            val lastStatus: StatusEntity? = repository.getLastStatus(
                trainRun.driverId,
                trainRun.startTime
            )
//      Создать статус "в поездке"
            val statusOnTrip = Status(
                0,
                trainRun.driverId,
                trainRun.startTime,
                1,
                lastStatus?.countNight?:0,
                lastStatus?.workedTime?:0,
                trainRun.id
            ).toDTO
            repository.createStatus(statusOnTrip)

//      Создать статус "отдых после поездки"
            val statusAfterTrip = Status(
                0,
                trainRun.driverId,
                trainRun.startTime + trainRun.travelTime,
                2,
                trainRun.countNight,
                lastStatus?.workedTime ?: (0
                        + trainRun.workTime),
                trainRun.id
            ).toDTO
repository.createStatus(statusAfterTrip)
//      Создать статус "в ожидании работы"
            val countNightWaitingForWork =
                if (statusAfterTrip.date.toLocalDateTime().toLocalDate().atStartOfDay() !=
                    statusAfterTrip.date.toLocalDateTime().plusHours(MIN_REST).toLocalDate()
                        .atStartOfDay()
                    && statusAfterTrip.date.toLocalDateTime() > statusAfterTrip.date.toLocalDateTime()
                        .toLocalDate().atStartOfDay()
                        .plusHours(nightPeriod.endInclusive.hour.toLong())
                ) 0 else trainRun.countNight

            val statusWaitingForWork = Status(
                0,
                trainRun.driverId,
                trainRun.startTime + trainRun.travelTime + MIN_REST * 3600000L,
                3,
                countNight = countNightWaitingForWork,
                lastStatus?.workedTime ?: (0
                        + trainRun.workTime),
                trainRun.id
            ).toDTO
            repository.createStatus(statusWaitingForWork)

//            Сщздать статус "в ожидании работы" с количеством ночей = 0

            if (statusWaitingForWork.countNight!=0 && statusAfterTrip.date.toLocalDateTime().toLocalDate() ==
                statusWaitingForWork.date.toLocalDateTime()
                    .toLocalDate()
            ) {
                val statusWaitingForWorkCountNightIsZero = Status(
                    0,
                    trainRun.driverId,
                    date = statusWaitingForWork.date.toLocalDateTime().toLocalDate().plusDays(1)
                        .atStartOfDay().plusHours(
                            nightPeriod.endInclusive.hour.toLong()
                        ).toLong(),
                    3,
                    0,
                    lastStatus?.workedTime ?: (0
                            + trainRun.workTime),
                    trainRun.id
                ).toDTO
                repository.createStatus(statusWaitingForWorkCountNightIsZero)
            } else if (statusWaitingForWork.countNight!=0 &&
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
                    0,
                    trainRun.driverId,
                    date = statusWaitingForWork.date.toLocalDateTime().toLocalDate()
                        .atStartOfDay().plusHours(
                            nightPeriod.endInclusive
                                .hour.toLong().toLocalDateTime().toLong()
                        ).toLong(),
                    3,
                    0,
                    lastStatus?.workedTime ?: (0
                            + trainRun.workTime),
                    trainRun.id
                ).toDTO
                repository.createStatus(statusWaitingForWorkCountNightIsZero)
            }
        }
    }
}