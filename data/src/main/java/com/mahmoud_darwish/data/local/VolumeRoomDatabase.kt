package com.mahmoud_darwish.data.local

import androidx.room.*
import com.mahmoud_darwish.data.local.model.VolumeEntity

@Database(entities = [VolumeEntity::class], version = 1)
@TypeConverters(AuthorsListConverters::class)
abstract class VolumeRoomDatabase : RoomDatabase() {
    abstract fun getVolumeEntityDao(): VolumeEntityDao
}

