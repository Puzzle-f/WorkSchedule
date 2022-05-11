package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.ScheduleDataBase
import com.example.workschedule.domain.*
import com.example.workschedule.ui.driver_edit.DriverEditViewModel
import com.example.workschedule.ui.drivers.DriversViewModel
import com.example.workschedule.ui.main.MainFragmentViewModel
import com.example.workschedule.ui.route_edit.DateTimePickerViewModel
import com.example.workschedule.ui.route_edit.RouteEditViewModel
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
    single<DomainRepository> { DomainRepositoryImpl(dataBase = get()) }
    viewModel {
        MainFragmentViewModel(
            GetAllTrainsRunListUseCase(repository = get()),
            GetAllDriversListUseCase(repository = get()),
            DeleteTrainRunUseCase(repository = get())
        )
    }
    viewModel { DriversViewModel() }
    viewModel { TrainsViewModel() }
    viewModel { DriverEditViewModel(
        GetDriverUseCase(repository = get()),
        GetAllTrainsListUseCase(repository = get()),
        SaveDriverUseCase(repository = get())
    ) }
    viewModel { RouteEditViewModel() }
    viewModel { DateTimePickerViewModel() }
    viewModel { TrainEditViewModel() }
}