package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.*
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.main.MainFragmentViewModel
import com.example.workschedule.ui.trainrun_edit.TrainRunEditViewModel
import com.example.workschedule.ui.train_edit.TrainEditViewModel
import com.example.workschedule.ui.trains.TrainsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    single {
        Room.databaseBuilder(
            get(), ScheduleDataBase::class.java,
            "ScheduleDB.db"
        ).build()
    }
    single<DomainRepository> { DomainRepositoryImpl(database = get()) }
    viewModel {
        MainFragmentViewModel(
            GetAllTrainsRunListUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            DeleteTrainRunUseCase(repository = get())
        )
    }
    viewModel {
        TrainRunEditViewModel(
            GetTrainRunUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            GetAllTrainsListUseCase(repository = get()),
            SaveTrainRunUseCase(repository = get())
        )
    }
    viewModel {
        DriversViewModel(
            GetAllDriversListUseCase(repository = get()),
            DeleteDriverUseCase(repository = get())
        )
    }
    viewModel {
        DriverEditViewModel(
            GetDriverUseCase(repository = get()),
            GetAllTrainsListUseCase(repository = get()),
            SaveDriverUseCase(repository = get())
        )
    }
    viewModel {
        TrainsViewModel(
            GetAllTrainsListUseCase(repository = get()),
            DeleteTrainUseCase(repository = get())
        )
    }
    viewModel {
        TrainEditViewModel(
            GetTrainUseCase(repository = get()),
            SaveTrainUseCase(repository = get())
        )
    }
}

