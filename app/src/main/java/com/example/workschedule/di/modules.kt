package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.MIGRATION_1_2
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.usecases.driver.*
import com.example.workschedule.domain.usecases.permission.GetPermissionsForDriverUseCase
import com.example.workschedule.domain.usecases.permission.SavePermissionForDriverIfNotAvailableUseCase
import com.example.workschedule.domain.usecases.permission.SavePermissionsUseCase
import com.example.workschedule.domain.usecases.train.DeleteDirectionUseCase
import com.example.workschedule.domain.usecases.train.GetAllDirectionsListUseCase
import com.example.workschedule.domain.usecases.train.GetDirectionUseCase
import com.example.workschedule.domain.usecases.train.SaveDirectionUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.main.MainFragmentViewModel
import com.example.workschedule.ui.schedule_all_drivers.SchedulersViewModel
import com.example.workschedule.ui.direction_edit.DirectionEditViewModel
import com.example.workschedule.ui.trainrun_edit.TrainRunEditViewModel
import com.example.workschedule.ui.trainrun_edit.trainrun_redact.TrainRunRedactViewModel
import com.example.workschedule.ui.direction.DirectionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(), ScheduleDataBase::class.java,
            "ScheduleDB.db"
        )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
    single<DomainRepository> { DomainRepositoryImpl(database = get()) }
    viewModel {
        MainFragmentViewModel(
            GetAllTrainsRunListUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            SaveTrainRunListUseCase(repository = get()),
            DeleteTrainRunUseCase(repository = get()),
            DeleteAllTrainRunUseCase(repository = get()),
            GetTrainRunListByDriverIdAfterDateUseCase(repository = get())
        )
    }
    viewModel {
        TrainRunEditViewModel(
            GetTrainRunUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            SaveTrainRunUseCase(repository = get()),
            SaveTrainRunListUseCase(repository = get())
        )
    }
    viewModel {
        TrainRunRedactViewModel(
            GetTrainRunUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            SaveTrainRunUseCase(repository = get()),
            SaveTrainRunListUseCase(repository = get())
        )
    }
    viewModel {
        DriversViewModel(
            GetAllDriversListUseCase(repository = get()),
            DeleteDriverUseCase(repository = get()),
            DeleteAllDriversUseCase(repository = get())
        )
    }
    viewModel {
        DriverEditViewModel(
            GetDriverUseCase(repository = get()),
            GetAllDirectionsListUseCase(repository = get()),
            SaveDriverUseCase(repository = get()),
            SavePermissionsUseCase(repository = get()),
            GetPermissionsForDriverUseCase(repository = get()),
            SavePermissionForDriverIfNotAvailableUseCase(repository = get())
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
}

