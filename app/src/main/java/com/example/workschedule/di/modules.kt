package com.example.workschedule.di

import androidx.room.Room
import com.example.workschedule.data.database.DriverDataBase
import com.example.workschedule.ui.main.WorkerEditViewModel
import com.example.workschedule.ui.workers.WorkersViewModel
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
    viewModel { WorkersViewModel() }
    viewModel { WorkerEditViewModel(get()) }
}