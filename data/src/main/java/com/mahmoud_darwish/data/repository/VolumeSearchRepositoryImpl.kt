package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.data.di.AppIOCoroutineScope
import com.mahmoud_darwish.data.local.VolumeEntityDao
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.mapper.toVolume
import com.mahmoud_darwish.data.mapper.toVolumeEntityList
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.util.UiText
import com.mahmoud_darwish.domain.model.Volume
import com.mahmoud_darwish.domain.repository.IVolumeSearchRepository
import com.mahmoud_darwish.domain.util.Resource
import com.mahmoud_darwish.domain.util.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VolumeSearchRepositoryImpl @Inject constructor(
    private val service: GoogleBooksApi,
    private val volumeEntityDao: VolumeEntityDao,
    private val uiText: UiText,
    @AppIOCoroutineScope
    private val scope: CoroutineScope
) : IVolumeSearchRepository {
    // this is later used to add a delay before a request is sent to Google books API server
    // to prevent sending too many requests while the user is typing
    private var job: Job? = null

    init {
        // when the repository is first created, a search must be initiated.
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

            emitLoadingAndValidateQueryStringOrEmitError { newQuery ->
                tryLoadingFromCacheOrRemoteIfNoCacheExists(newQuery)
            }
        }
    }

    private suspend fun tryLoadingFromCacheOrRemoteIfNoCacheExists(
        newQuery: String
    ) {
        try {
            val cachedResults = getCachedResults(newQuery)

            if (cachedResults.isNotEmpty())
                emitResults(Resource.Success(cachedResults, Source.CACHE))
            else
                forceLoadingFromServer()

        } catch (e: Exception) {
            e.printStackTrace()

            emitError(e.message)
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
    override suspend fun getVolumeById(id: String): Volume =
        volumeEntityDao.getVolumeEntity(id).toVolume()


    override fun forceLoadingFromServer() {
        scope.launch {
            emitLoading()

            emitLoadingAndValidateQueryStringOrEmitError { newQuery ->
                try {
                    loadAndCacheNewResults(newQuery)
                    emitNewlyCachedResults(newQuery)
                } catch (e: Exception) {
                    e.printStackTrace()

                    emitError(e.message)
                }
            }
        }
    }

    /**
     * get previously cached results
     * */
    private suspend fun getCachedResults(query: String) =
        volumeEntityDao.volumeSearch(query).toVolumeList()


    private fun emitResults(
        success: Resource.Success<List<Volume>>
    ) {
        searchResult.value = success
    }

    private suspend fun loadAndCacheNewResults(newQuery: String) {
        // searching with the Google books service
        val volumesToCache =
            service.search(newQuery).items.toVolumeList().toVolumeEntityList()
        // caching the results
        volumeEntityDao.insert(volumesToCache)
    }

    /**
     * This is to be called after making request to the server to cache the response.
     * */
    private suspend fun emitNewlyCachedResults(
        newQuery: String
    ) {
        // emitting the results after caching them to adhere to the single source of truth principle
        val serverRequestResults = getCachedResults(newQuery)
        // here the source is considered remote because the data is as fresh as possible.
        val success = Resource.Success(serverRequestResults, Source.REMOTE)

        emitNewResultsOrErrorIfEmpty(success)
    }

    private fun emitNewResultsOrErrorIfEmpty(results: Resource.Success<List<Volume>>) {
        if (results.data.isEmpty()) emitError(uiText.noResultsFoundError)
        else emitResults(results)
    }

    private fun emitError(message: String?) {
        searchResult.value = Resource.Error(message ?: uiText.unknownErrorMessage)
    }

    private fun emitLoading() {
        searchResult.value = Resource.Loading
    }

    private suspend fun emitLoadingAndValidateQueryStringOrEmitError(
        onValidValue: suspend (query: String) -> Unit,
    ) {
        emitLoading()

        val queryString = query.value.trim().lowercase()

        if (queryString.isNotBlank()) {
            onValidValue(queryString)
        } else {
            emitError(uiText.searchTermNotEnteredErrorMessage)
        }
    }
}
