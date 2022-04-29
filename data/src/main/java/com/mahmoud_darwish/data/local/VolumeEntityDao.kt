package com.mahmoud_darwish.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_darwish.data.local.model.VolumeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VolumeEntityDao {
    @Query("SELECT * FROM VolumeEntity WHERE id = :id LIMIT 1")
    suspend fun getVolumeEntity(id: String): VolumeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(volumes: List<VolumeEntity>)

    @Query("SELECT * FROM VolumeEntity WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%' OR LOWER(title) = LOWER(:title)")
    suspend fun volumeSearch(title: String): List<VolumeEntity>
}