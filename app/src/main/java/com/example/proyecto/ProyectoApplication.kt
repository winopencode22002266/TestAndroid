package com.example.proyecto

import android.app.Application
import com.example.proyecto.data.local.database.NoteDatabase
import com.example.proyecto.data.repository.NoteRepository
import com.example.proyecto.data.security.EncryptionManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ProyectoApplication : Application() {

    private val appModule = module {
        single { NoteDatabase.getInstance(get()) }
        single { get<NoteDatabase>().noteDao() }
        single { EncryptionManager(get()) }
        single { NoteRepository(get(), get()) }
        viewModel { NoteViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProyectoApplication)
            modules(appModule)
        }
    }
}
