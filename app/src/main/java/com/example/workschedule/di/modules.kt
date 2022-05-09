package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.DomainRepositoryImpl
import com.example.workschedule.data.database.DriverDataBase
import com.example.workschedule.domain.DomainRepository
import com.example.workschedule.domain.GetAllTrainsRunListUseCase
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
            get(), DriverDataBase::class.java,
            "DriverDB.db"
        ).build()
    }
    single { get<DriverDataBase>().driverDao() }
    single { get<DriverDataBase>().trainDao() }
    single { get<DriverDataBase>().trainRunDao() }
    single<DomainRepository> { DomainRepositoryImpl() }
    viewModel { MainFragmentViewModel(GetAllTrainsRunListUseCase(get())) }
    viewModel { DriversViewModel() }
    viewModel { TrainsViewModel() }
    viewModel { DriverEditViewModel(db = get()) }
    viewModel { RouteEditViewModel() }
    viewModel { DateTimePickerViewModel() }
    viewModel { TrainEditViewModel() }
}