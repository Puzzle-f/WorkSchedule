package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.usecases.driver.*
import com.example.workschedule.domain.usecases.logiс.CreateListStatusForTrainRunUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverAfterHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.FindDriverBeforeHorizonUseCase
import com.example.workschedule.domain.usecases.logiс.RecalculateStatusesForDriverAfterTimeUseCase
import com.example.workschedule.domain.usecases.permission.AddPermissionsUseCase
import com.example.workschedule.domain.usecases.permission.DeletePermissionUseCase
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.permission.GetPermissionsForDriverUseCase
import com.example.workschedule.domain.usecases.status.CreateStatusUseCase
import com.example.workschedule.domain.usecases.status.DeleteStatusForTrainRunIdUseCase
import com.example.workschedule.domain.usecases.status.DeleteStatusesForDriverAfterDateUseCase
import com.example.workschedule.domain.usecases.status.GetLastStatusUseCase
import com.example.workschedule.domain.usecases.train.DeleteDirectionUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.train.GetDirectionUseCase
import com.example.workschedule.domain.usecases.train.SaveDirectionUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.domain.usecases.weekend.GetWeekendsUseCase
import com.example.workschedule.domain.usecases.weekend.SaveWeekendUseCase
import com.example.workschedule.ui.direction.DirectionsViewModel
import com.example.workschedule.ui.direction_edit.DirectionEditViewModel
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.main.MainFragmentViewModel
import com.example.workschedule.ui.schedule_all_drivers.SchedulersViewModel
import com.example.workschedule.ui.trainrun_edit.TrainRunEditViewModel
import com.example.workschedule.ui.weekend.WeekendViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(), ScheduleDataBase::class.java,
            "ScheduleDB.db"
        )
//            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<DomainRepository> { DomainRepositoryImpl(database = get()) }

    single {
        RecalculateStatusesForDriverAfterTimeUseCase(
            DeleteStatusesForDriverAfterDateUseCase(repository = get()),
            GetTrainRunListByDriverIdAfterDateUseCase(repository = get()),
            CreateListStatusForTrainRunUseCase(
                GetLastStatusUseCase(repository = get()),
                CreateStatusUseCase(repository = get())
            )
        )
    }
    viewModel {
        MainFragmentViewModel(
            GetAllTrainsRunListUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            DeleteTrainRunUseCase(repository = get()),
            DeleteAllTrainRunUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            recalculateStatusesForForDriverAfterTimeUseCase = get(),
            FindDriverBeforeHorizonUseCase(repository = get()),
            FindDriverAfterHorizonUseCase(
                recalculateStatusesForForDriverAfterTimeUseCase = get(),
                GetDriverIdByPermissionUseCase(repository = get()),
                GetLastStatusUseCase(repository = get()),
                CreateStatusUseCase(repository = get()),
                UpdateTrainRunUseCase(repository = get())
            ),
            ClearDriverForTrainRunAfterDateUseCase(repository = get()),
            DeleteStatusForTrainRunIdUseCase(repository = get())
        )
    }
    viewModel {
        TrainRunEditViewModel(
            GetTrainRunUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            SaveTrainRunUseCase(repository = get()),
            SaveTrainRunListUseCase(repository = get()),
            UpdateTrainRunUseCase(repository = get()),
            GetTrainRunByNumberAndStartTimeUseCase(repository = get()),
            recalculateStatusesForForDriverAfterTimeUseCase = get()
        )
    }
    viewModel {
        DriversViewModel(
            GetAllDriversListUseCase(repository = get()),
            DeleteDriverUseCase(repository = get()),
            DeleteAllDriversUseCase(repository = get()),
            ClearDriverForTrainRunUseCase(repository = get())
        )
    }
    viewModel {
        DriverEditViewModel(
            GetDriverUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            SaveDriverUseCase(repository = get()),
            AddPermissionsUseCase(repository = get()),
            GetPermissionsForDriverUseCase(repository = get()),
            UpdateDriverUseCase(repository = get()),
            DeletePermissionUseCase(repository = get()),
            GetDriverByPersonalNumberAndSurnameUseCase(repository = get())
        )
    }
    viewModel {
        DirectionsViewModel(
            GetAllDirectionsListUseCase(repository = get()),
            DeleteDirectionUseCase(repository = get())
        )
    }
    viewModel {
        DirectionEditViewModel(
            GetDirectionUseCase(repository = get()),
            SaveDirectionUseCase(repository = get())
        )
    }
    viewModel {
        SchedulersViewModel(
            GetAllDriversListUseCase(repository = get()),
            GetTrainRunListForDriverUseCase(repository = get())
        )
    }
    viewModel {
        WeekendViewModel(
            GetWeekendsUseCase(repository = get()),
            SaveWeekendUseCase(repository = get())
        )
    }
}

