package com.mahmoud_darwish.data.local

import androidx.room.*
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.data.local.model.VolumeEntity

@Database(entities = [VolumeEntity::class, Favorite::class], version = 1)
@TypeConverters(AuthorsListConverters::class)
abstract class VolumeRoomDatabase : RoomDatabase() {
    abstract fun getVolumeEntityDao(): VolumeEntityDao
    abstract fun getFavoriteEntityDao(): FavoriteEntityDao
}

