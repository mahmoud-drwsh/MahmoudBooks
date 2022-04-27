package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.local.model.toVolumeList
import com.mahmoud_darwish.data.mapper.toVolumeEntityList
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IVolumeSearchRepository
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VolumeSearchRepositoryImpl @Inject constructor(
    private val service: GoogleBooksApi,
    private val volumeEntityDao: VolumeEntityDao,
) : IVolumeSearchRepository {

    private val scope: CoroutineScope = CoroutineScope(Job() + IO)
    private var job: Job? = null


    init {
        performSearch()
    }

    override val searchResult: MutableStateFlow<Resource<List<Volume>>> =
        MutableStateFlow(Resource.Loading)

    private val queryString: MutableStateFlow<String> = MutableStateFlow("kotlin")

    override val query: StateFlow<String> = queryString

    private fun performSearch() {
        job?.cancel()
        job = scope.launch {
            delay(500L)

            val newQuery = query.value

            if (newQuery.isBlank()) {
                searchResult.value = Resource.Error("Please enter a title to search for")
                return@launch
            }

            searchResult.value = Resource.Loading

            val emitSuccess = { success: Resource.Success<List<Volume>> ->
                searchResult.value =
                    if (success.data.isEmpty()) Resource.Error("No results were found")
                    else success
            }

            try {
                // searching with the Google books service
                val volumesToCache =
                    service.search(newQuery).items.toVolumeList().toVolumeEntityList()
                // caching the results
                volumeEntityDao.insert(volumesToCache)
                // emitting the results after caching them to adhere to the single source of truth principle
                emitSuccess(
                    Resource.Success(
                        volumeEntityDao.volumeSearch(newQuery).toVolumeList(), Source.REMOTE
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()

                // loading data from the local cache
                val volumeSearch = volumeEntityDao.volumeSearch(newQuery)
                emitSuccess(
                    Resource.Success(
                        volumeSearch.toVolumeList(),
                        Source.CACHE,
                        "Due to network related issues, the results below are from the cache."
                    )
                )
            }
        }
    }

    override fun setSearchQuery(title: String) {
        queryString.value = title
        performSearch()
    }

    // since the search results are always cached, when a user wants to view a specific volume, it can just be obtained from the cache.
    override fun getVolumeById(id: String): Flow<Resource<Volume>> = flow {
        volumeEntityDao.getVolume(id)
    }
}