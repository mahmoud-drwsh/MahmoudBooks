package com.mahmoud_darwish.data.di

import com.mahmoud_darwish.data.local.VolumeRoomDatabase
import com.mahmoud_darwish.data.local.getVolumeRoomDatabaseInstance
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.remote.getBooksServiceInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.module

@Module
@ComponentScan("com.mahmoud_darwish.data")
class AppModule

@Suppress("RemoveExplicitTypeArguments")
val AppMainKoinModule: org.koin.core.module.Module = module {
    single<GoogleBooksApi> { getBooksServiceInstance() }

    single { getVolumeRoomDatabaseInstance(androidContext()) }

    single { get<VolumeRoomDatabase>().getVolumeEntityDao() }

    single { get<VolumeRoomDatabase>().getFavoriteEntityDao() }

    single<CoroutineScope> { CoroutineScope(IO + SupervisorJob()) }
}
