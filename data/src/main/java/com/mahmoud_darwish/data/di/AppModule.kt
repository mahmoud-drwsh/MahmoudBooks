package com.mahmoud_darwish.data.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.mahmoud_darwish.core.repository.IFavoritesRepository
import com.mahmoud_darwish.core.repository.IModuleInstallationRepository
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.data.local.FavoriteEntityDao
import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.local.VolumeRoomDatabase
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.remote.getBooksServiceInstance
import com.mahmoud_darwish.data.repository.FavoritesRepositoryImpl
import com.mahmoud_darwish.data.repository.IModuleInstallationRepositoryImpl
import com.mahmoud_darwish.data.repository.VolumeSearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Qualifier
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBooksRepository(repo: VolumeSearchRepositoryImpl): IVolumeSearchRepository = repo

    @Provides
    @Singleton
    fun provideFavoritesRepository(repo: FavoritesRepositoryImpl): IFavoritesRepository = repo

    @Provides
    @Singleton
    fun provideModuleInstallationRepository(repo: IModuleInstallationRepositoryImpl): IModuleInstallationRepository =
        repo

    @Provides
    @Singleton
    fun providePreferencesDataStore(app: Application): DataStore<Preferences> = app.dataStore

    @Provides
    @Singleton
    fun provideBooksService(): GoogleBooksApi = getBooksServiceInstance()

    @Provides
    @Singleton
    fun provideVolumeRoomDatabase(app: Application): VolumeRoomDatabase = Room
        .databaseBuilder(app.applicationContext, VolumeRoomDatabase::class.java, "volume_db.db")
        .build()

    @Provides
    @Singleton
    fun provideVolumeEntityDao(volumeRoomDatabase: VolumeRoomDatabase): VolumeEntityDao =
        volumeRoomDatabase.getVolumeEntityDao()

    @Provides
    @Singleton
    fun provideFavoriteEntityDao(volumeRoomDatabase: VolumeRoomDatabase): FavoriteEntityDao =
        volumeRoomDatabase.getFavoriteEntityDao()

    @AppIoCoroutineScope
    @Provides
    @Singleton
    fun provideAppCoroutineScope(): CoroutineScope = CoroutineScope(IO)
}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppIoCoroutineScope