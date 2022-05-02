package com.mahmoud_darwish.favorites

import android.content.Context
import com.mahmoud_darwish.data.di.FavoritesModuleDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [FavoritesModuleDependencies::class])
interface FavoritesComponent {

    fun inject(activity: FavoritesActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoritesModuleDependencies: FavoritesModuleDependencies): Builder
        fun build(): FavoritesComponent
    }
}