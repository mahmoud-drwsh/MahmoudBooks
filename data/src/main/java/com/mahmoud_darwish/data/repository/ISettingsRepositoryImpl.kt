package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.core.repository.ISettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ISettingsRepositoryImpl @Inject constructor(
    private val dao: VolumeEntityDao
) : ISettingsRepository {


}