package com.mahmoud_darwish.core.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.util.CachedResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IVolumeSearchRepository {
    fun setSearchQuery(title: String)

    suspend fun getVolumeById(volumeId: String): Volume

    /**
     * This flow will contain the results of the search =,
     * */
    val searchResultsResourceFlow: Flow<CachedResource<List<Volume>>>

    /**
     * This flow will contain the string set with the method setSearchQuery.
     * */
    val queryStringFlow: StateFlow<String>

    /**
     * This is used when the user refreshes the page in order to ensure that the data the user
     * is shown later is directly from the remote source
     * */
    fun forceLoadingFromRemoteSource()
}