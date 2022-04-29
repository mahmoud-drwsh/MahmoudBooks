package com.mahmoud_darwish.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud_darwish.data.local.model.Favorite
import com.mahmoud_darwish.data.local.model.VolumeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEntityDao {
    @Query("SELECT VolumeEntity.* FROM VolumeEntity join Favorite on Favorite.id = VolumeEntity.id")
    suspend fun getFavorites(): List<VolumeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE id = :id)")
    fun isFavorite(id: String): Flow<Boolean>
}