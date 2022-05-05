package com.mahmoud_darwish.gbooks

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.mahmoud_darwish.data.di.AppKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(AppKoinModule)
        }
    }
}