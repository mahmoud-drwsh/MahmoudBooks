package com.mahmoud_darwish.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_darwish.data.local.model.VolumeEntity

@Dao
interface VolumeEntityDao {
    @Query("SELECT * FROM VolumeEntity")
    suspend fun getAllChached(): List<VolumeEntity>

    @Query("DELETE FROM VolumeEntity")
    suspend fun clearCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(volumes: List<VolumeEntity>)

    @Query("SELECT * FROM VolumeEntity WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%' OR LOWER(title) = lOWER(:title)")
    suspend fun volumeSearch(title: String)
}