package com.mahmoud_darwish.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_darwish.data.local.model.VolumeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VolumeEntityDao {
    @Query("SELECT * FROM VolumeEntity")
    suspend fun getAllCached(): List<VolumeEntity>

    @Query("SELECT * FROM VolumeEntity WHERE id = :id LIMIT 1")
    suspend fun getVolume(id: String): VolumeEntity

    @Query("DELETE FROM VolumeEntity")
    suspend fun clearCache()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(volumes: List<VolumeEntity>)

    @Query("SELECT * FROM VolumeEntity WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%' OR LOWER(title) = lOWER(:title)")
    suspend fun volumeSearch(title: String): List<VolumeEntity>
}