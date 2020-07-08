package com.example.bloodbank

import android.app.Application
import com.example.bloodbank.ui.viewModels.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
                viewModelModule
        )

        startKoin {
            androidContext(this@Application)
            modules(modules)
        }
    }
}