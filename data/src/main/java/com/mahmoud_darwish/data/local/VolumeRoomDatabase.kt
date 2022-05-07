package com.mahmoud_darwish.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoud_darwish.data.local.dao.FavoriteEntityDao
import com.mahmoud_darwish.data.local.dao.VolumeEntityDao
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.data.local.model.VolumeEntity
import com.mahmoud_darwish.data.local.util.StringListConverters
import com.mahmoud_darwish.data.local.util.databaseFileName


@Database(entities = [VolumeEntity::class, Favorite::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverters::class)
abstract class VolumeRoomDatabase : RoomDatabase() {
    abstract fun getVolumeEntityDao(): VolumeEntityDao

    abstract fun getFavoriteEntityDao(): FavoriteEntityDao
}

/**
 * This will be used by the dependency injection framework to generate a singleton instance
 * */
fun getVolumeRoomDatabaseInstance(context: Context): VolumeRoomDatabase = Room
    .databaseBuilder(context, VolumeRoomDatabase::class.java, databaseFileName)
    .build()