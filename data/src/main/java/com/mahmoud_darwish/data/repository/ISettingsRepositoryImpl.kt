package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.core.repository.ISettingsRepository
import com.mahmoud_darwish.data.local.VolumeEntityDao
import org.koin.core.annotation.Single

@Single
class ISettingsRepositoryImpl constructor(
    private val dao: VolumeEntityDao
) : ISettingsRepository