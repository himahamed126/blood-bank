package com.example.bloodbank

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }


        startKoin {
            androidContext(this@Application)
//            modules(modules)
        }
    }

    companion object {

        private var instance: com.example.bloodbank.Application? = null

        fun getInstance(): com.example.bloodbank.Application? {
            return instance
        }
    }

}