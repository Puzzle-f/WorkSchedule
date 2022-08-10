package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.*
import com.example.workschedule.domain.usecases.driver.*
import com.example.workschedule.domain.usecases.train.DeleteTrainUseCase
import com.example.workschedule.domain.usecases.train.GetAllTrainsListUseCase
import com.example.workschedule.domain.usecases.train.GetTrainUseCase
import com.example.workschedule.domain.usecases.train.SaveTrainUseCase
import com.example.workschedule.domain.usecases.trainrun.*
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.main.MainFragmentViewModel
import com.example.workschedule.ui.schedule_all_drivers.SchedulersViewModel
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
            SaveTrainRunListUseCase(repository = get()),
            DeleteTrainRunUseCase(repository = get()),
            DeleteAllTrainRunUseCase(repository = get())
        )
    }
    viewModel {
        TrainRunEditViewModel(
            GetTrainRunUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            GetAllTrainsListUseCase(repository = get()),
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
    viewModel {
        SchedulersViewModel(
            GetAllDriversListUseCase(repository = get()),
            GetTrainRunListForDriverUseCase(repository = get())
        )
    }
}

