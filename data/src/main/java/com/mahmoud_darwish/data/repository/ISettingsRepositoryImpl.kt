package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.domain.repository.ISettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ISettingsRepositoryImpl @Inject constructor(
    private val dao: VolumeEntityDao
) : ISettingsRepository {
    override suspend fun clearCache() {
        dao.clearCache()
    }


}