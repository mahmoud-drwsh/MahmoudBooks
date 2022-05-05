package com.mahmoud_darwish.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.data.local.VolumeRoomDatabase
import com.mahmoud_darwish.data.local.getVolumeRoomDatabaseInstance
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.remote.getBooksServiceInstance
import com.mahmoud_darwish.data.repository.FavoritesRepositoryImpl
import com.mahmoud_darwish.data.repository.ModuleInstallationRepositoryImpl
import com.mahmoud_darwish.data.repository.VolumeSearchRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("RemoveExplicitTypeArguments")
val AppKoinModule = module {
    single<IVolumeSearchRepository> { VolumeSearchRepositoryImpl(get(), get(), get(), get()) }

    single<IFavoritesRepository> { FavoritesRepositoryImpl(get(), get(), get()) }

    single<IModuleInstallationRepository> { ModuleInstallationRepositoryImpl(get(), get(), get()) }

    single<DataStore<Preferences>> { androidContext().dataStore }

    single<GoogleBooksApi> { getBooksServiceInstance() }

    single { getVolumeRoomDatabaseInstance(androidContext()) }

    single { get<VolumeRoomDatabase>().getVolumeEntityDao() }

    single { get<VolumeRoomDatabase>().getFavoriteEntityDao() }

    single<CoroutineScope> { CoroutineScope(IO) }
}

class AppModule