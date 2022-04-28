package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.local.model.toVolumeList
import com.mahmoud_darwish.data.mapper.toVolumeEntityList
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.util.UiText
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
    private val uiText: UiText,
) : IVolumeSearchRepository {

    private val scope: CoroutineScope = CoroutineScope(Job() + IO)
    private var job: Job? = null

    init {
        performSearch()
    }

    override val searchResult: MutableStateFlow<Resource<List<Volume>>> =
        MutableStateFlow(Resource.Loading)

    private val _queryString: MutableStateFlow<String> = MutableStateFlow(uiText.initialSearchTerm)
    override val query: StateFlow<String> = _queryString

    /*
     * below, the program will try to search the cache, then if results were not found,
     * the program will request from the server.
     * The program works like this to reduce the number of calls made to the server.
     *
     * The pseudo-code:
     * 1. if the query string is empty emit an error state.
     * 2. search the cache and emit the results is any are found.
     * 3. if no cached results were found, send a search request to the server.
     * 4. emit the returned results from the server after caching them.
     * 5. emit an error with a message explaining that no results were found.
     * */
    private fun performSearch() {
        job?.cancel()
        job = scope.launch {
            delay(500L)

            val newQuery = prepareSearchQueryString()

            if (newQuery.isBlank()) {
                emitError(uiText.searchTermNotEnteredErrorMessage)
                return@launch
            }

            emitLoading()

            try {
                val cachedResults = getCachedResults(newQuery)

                if (cachedResults.isNotEmpty())
                    emitCachedResults(cachedResults)
                else {
                    loadAndCacheNewResults(newQuery)
                    emitNewlyCachedResults(newQuery)
                }
            } catch (e: Exception) {
                e.printStackTrace()

                emitError(e.message)
            }
        }
    }

    override fun setSearchQuery(title: String) {
        _queryString.value = title.lowercase()
        performSearch()
    }

    /*
    * Since all the requested results will be cached, when a user wants to view a specific volume,
    * the volume can be found in the cache.
    * */
    override fun getVolumeById(id: String): Flow<Resource<Volume>> = flow {
        volumeEntityDao.getVolume(id)
    }

    private suspend fun getCachedResults(query: String) =
        volumeEntityDao.volumeSearch(query).toVolumeList()

    private fun emitCachedResults(cachedResults: List<Volume>) {
        searchResult.value = Resource.Success(cachedResults, Source.CACHE)
    }

    private suspend fun loadAndCacheNewResults(newQuery: String) {
        // searching with the Google books service
        val volumesToCache =
            service.search(newQuery).items.toVolumeList().toVolumeEntityList()
        // caching the results
        volumeEntityDao.insert(volumesToCache)
    }

    private suspend fun emitNewlyCachedResults(
        newQuery: String
    ) {
        // emitting the results after caching them to adhere to the single source of truth principle
        val success = Resource.Success(
            volumeEntityDao.volumeSearch(newQuery).toVolumeList(), Source.REMOTE
        )
        searchResult.value =
            if (success.data.isEmpty()) Resource.Error(uiText.noResultsFoundError)
            else success
    }

    private fun emitError(message: String?) {
        searchResult.value = Resource.Error(message ?: uiText.unknownErrorMessage)
    }

    private fun emitSuccess(success: Resource.Success<List<Volume>>) {
        searchResult.value = success
    }

    private fun emitLoading() {
        searchResult.value = Resource.Loading
    }

    private fun prepareSearchQueryString() = query.value.trim().lowercase()
}
