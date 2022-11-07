package com.example.newyorktimes

import android.app.Application
import com.example.newyorktimes.di.simpleModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NYTApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@NYTApplication)
            modules(simpleModule)
        }
    }
}