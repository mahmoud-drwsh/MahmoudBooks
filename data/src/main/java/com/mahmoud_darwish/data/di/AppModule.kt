package com.mahmoud_darwish.data.di

import android.app.Application
import androidx.room.Room
import com.mahmoud_darwish.data.local.VolumeRoomDatabase
import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.remote.getBooksServiceInstance
import com.mahmoud_darwish.data.repository.VolumeSearchRepositoryImpl
import com.mahmoud_darwish.domain.repository.IVolumeSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBooksRepository(booksRepositoryImpl: VolumeSearchRepositoryImpl): IVolumeSearchRepository =
        booksRepositoryImpl

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
}