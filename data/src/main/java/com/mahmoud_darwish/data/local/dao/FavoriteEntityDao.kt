package com.mahmoud_darwish.data.local.dao

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
    fun getFavoriteVolumes(): Flow<List<VolumeEntity>>

    // replace is used here just to ensure the entry is inserted :)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Query("DELETE FROM Favorite WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE id = :id)")
    fun isVolumeFavorite(id: String): Flow<Boolean>
}