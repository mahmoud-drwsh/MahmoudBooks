package com.mahmoud_darwish.data.local.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
class VolumeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: List<String>,
    val aboutTheBook: String,
    val thumbnail: String,
    val thumbnailLarge: String,
    val price: Int,
    val pages: Int,
    val rating: Double = 0.0,
    val reviewsNumber: Int = 0
)

@Dao
interface VolumeEntityDao {
    @Query("SELECT * FROM VolumeEntity")
    suspend fun getAllChached(): List<VolumeEntity>

    @Query("DELETE FROM VolumeEntity")
    suspend fun clearCache()

    @Query("SELECT * FROM VolumeEntity WHERE LOWER(title) LIKE '%' || LOWER(:title) || '%' OR LOWER(title) = lOWER(:title)")
    suspend fun volumeSearch(title: String)
}