package com.mahmoud_darwish.gbooks

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.mahmoud_darwish.data.di.AppMainKoinModule
import com.mahmoud_darwish.data.di.AppModule
import com.mahmoud_darwish.ui_main.di.MainUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

// inheriting SplitCompatApplication here is needed to get dynamic feature installation API to work
class App : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        // setting up Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(
                AppMainKoinModule,
                AppModule().module,
                MainUiModule().module
            )
        }
    }
}