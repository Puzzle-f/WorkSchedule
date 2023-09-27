package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.usecases.distraction.*
import com.example.workschedule.domain.usecases.driver.*
import com.example.workschedule.domain.usecases.logiс.*
import com.example.workschedule.domain.usecases.permission.AddPermissionsUseCase
import com.example.workschedule.domain.usecases.permission.DeletePermissionUseCase
import com.example.workschedule.domain.usecases.permission.GetDriverIdByPermissionUseCase
import com.example.workschedule.domain.usecases.permission.GetPermissionsForDriverUseCase
import com.example.workschedule.domain.usecases.status.*
import com.example.workschedule.domain.usecases.train.DeleteDirectionUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.train.GetDirectionUseCase
import com.example.workschedule.domain.usecases.train.SaveDirectionUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.domain.usecases.weekend.*
import com.example.workschedule.ui.direction.DirectionsViewModel
import com.example.workschedule.ui.direction_edit.DirectionEditViewModel
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.finddriver.SelectionDriverViewModel
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
        GetLastStatusWeekendUseCase(repository = get())
    }
    single {
        GetLastStatusDistractionUseCase(repository = get())
    }

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
    single {
        FindDriverBeforeHorizonUseCase(
            recalculateStatusesForDriverAfterTimeUseCase = get(),
            GetDriverIdByPermissionUseCase(repository = get()),
            GetLastStatusUseCase(repository = get()),
            GetDriverUseCase(repository = get()),
            CreateStatusUseCase(repository = get()),
            UpdateTrainRunUseCase(repository = get()),
            GetStatusesForTrainRunUseCase(repository = get()),
            GetStatusesForDriverBetweenDateUseCase(repository = get()),
            DeleteStatusForTrainRunIdUseCase(repository = get()),
            GetTrainRunUseCase(repository = get()),
            CheckWeekendUseCase(getLastStatusWeekendUseCase = get()),
            CheckDistractionUseCase(getLastStatusDistractionUseCase = get())
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
            FindDriverAfterHorizonUseCase(
                recalculateStatusesForForDriverAfterTimeUseCase = get(),
                GetDriverIdByPermissionUseCase(repository = get()),
                GetLastStatusUseCase(repository = get()),
                CreateStatusUseCase(repository = get()),
                UpdateTrainRunUseCase(repository = get()),
                GetStatusesForTrainRunUseCase(repository = get()),
                GetStatusesForDriverBetweenDateUseCase(repository = get()),
                DeleteStatusForTrainRunIdUseCase(repository = get()),
                CheckWeekendUseCase(getLastStatusWeekendUseCase = get()),
                CheckDistractionUseCase(getLastStatusDistractionUseCase = get())
            ),
            ClearDriverForTrainRunAfterDateUseCase(repository = get()),
            DeleteStatusForTrainRunIdUseCase(repository = get()),
            CheckWeekendUseCase(getLastStatusWeekendUseCase = get()),
            ClearDriverForTrainRunUseCase(repository = get()),
            GetStatusesForTrainRunUseCase(repository = get()),
            CheckDistractionUseCase(getLastStatusDistractionUseCase = get())
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
            ClearDriverForAllTrainRunUseCase(repository = get())
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
            SaveWeekendUseCase(repository = get()),
            DeleteWeekendUseCase(repository = get()),
            DeleteAllWeekendsForDriverUseCase(repository = get()),
            GetDistractionUseCase(repository = get()),
            SaveDistractionUseCase(repository = get()),
            DeleteDistractionUseCase(repository = get()),
            DeleteAllDistractionsForDriverUseCase(repository = get()),
            GetLastStatusDistractionUseCase(repository = get())
        )
    }
    viewModel {
        SelectionDriverViewModel(
            GetAllDriversListUseCase(repository = get()),
            GetLastStatusUseCase(repository = get()),
            GetTrainRunUseCase(repository = get()),
            findDriverBeforeHorizonUseCase = get(),
            UpdateTrainRunUseCase(repository = get()),
            recalculateStatusesForDriverAfterTimeUseCase = get(),
            CleanDriverForIntersectionsUseCase(
                GetStatusesForTrainRunUseCase(repository = get()),
                GetStatusesForDriverBetweenDateUseCase(repository = get()),
                ClearDriverForTrainRunUseCase(repository = get())),
            SetDriverToTrainRunUseCase(repository = get())
        )
    }
}

