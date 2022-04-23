//package com.example.workschedule.di

//import androidx.room.Room
//import com.example.workschedule.data.database.personal.PersonalDataBase
//import org.koin.core.context.loadKoinModules
//import org.koin.dsl.module

//fun injectDependencies() = loadModules
//
//private val loadModules by lazy {
//    loadKoinModules(listOf(application))
//}
//
//val application = module {
//    single {
//        Room.databaseBuilder(
//            get(), PersonalDataBase::class.java,
//            "personalDB.db"
//        ).build()
//    }
//    single { get<PersonalDataBase>().personalDao() }
//}