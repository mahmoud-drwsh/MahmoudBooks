package com.mahmoud_darwish.data.di

import com.mahmoud_darwish.core.repository.IFavoritesRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoritesModuleDependencies {
    fun favoritesRepository(): IFavoritesRepository
}