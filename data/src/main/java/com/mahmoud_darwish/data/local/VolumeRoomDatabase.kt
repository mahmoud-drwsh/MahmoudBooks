package com.mahmoud_darwish.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoud_darwish.data.local.model.VolumeEntity
import com.mahmoud_darwish.data.local.model.VolumeEntityDao

@Database(entities = [VolumeEntity::class], version = 1)
abstract class VolumeRoomDatabase : RoomDatabase() {
    abstract fun getVolumeEntityDao(): VolumeEntityDao
}

