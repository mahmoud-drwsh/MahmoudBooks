package com.mahmoud_darwish.data.repository

import com.mahmoud_darwish.core.model.Volume
import com.mahmoud_darwish.core.repository.IVolumeSearchRepository
import com.mahmoud_darwish.core.util.CachedResource
import com.mahmoud_darwish.core.util.ResponseSource
import com.mahmoud_darwish.data.local.dao.VolumeEntityDao
import com.mahmoud_darwish.data.mapper.toVolume
import com.mahmoud_darwish.data.mapper.toVolumeEntityList
import com.mahmoud_darwish.data.mapper.toVolumeList
import com.mahmoud_darwish.data.remote.GoogleBooksApi
import com.mahmoud_darwish.data.util.UiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single


@Single(binds = [IVolumeSearchRepository::class])
class VolumeSearchRepositoryImpl(
    private val service: GoogleBooksApi,
    private val volumeEntityDao: VolumeEntityDao,
    private val uiText: UiText,
    private val scope: CoroutineScope
) : IVolumeSearchRepository {
    // this is later used to add a delay before a request is sent to Google books API server
    // to prevent sending too many requests while the user is typing
    private var job: Job? = null

    init {
        // when the repository is first created, a search must be initiated.
        performSearch()
    }

    override val searchResultsResourceFlow: MutableStateFlow<CachedResource<List<Volume>>> =
        MutableStateFlow(CachedResource.Loading)

    private val _queryString: MutableStateFlow<String> =
        MutableStateFlow(uiText.initialSearchTerm)
    override val queryStringFlow: StateFlow<String> = _queryString

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
            /**
             * This delay is added so that only when the user stops typing for 500 milliseconds,
             * the search would be executed.
             * */
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
                emitResults(CachedResource.Success(cachedResults, ResponseSource.CACHE))
            else
                forceLoadingFromRemoteSource()

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
    override suspend fun getVolumeById(volumeId: String): Volume =
        volumeEntityDao.getVolumeEntity(volumeId).toVolume()


    override fun forceLoadingFromRemoteSource() {
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

    /**
     * Encapsulation of the logic of publishing the results to the listeners
     * */
    private fun emitResults(success: CachedResource.Success<List<Volume>>) {
        searchResultsResourceFlow.value = success
    }

    /**
     * This function encapsulated the logic of requesting data, caching it, and passing it up to the requester.
     * The reason this is needed is to make sure that the single source of truth principle is applied.
     * */
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
        // emitting the results after caching them to adhere to the single responseSource of truth principle
        val serverRequestResults = getCachedResults(newQuery)
        // here the responseSource is considered remote because the data is as fresh as possible.
        val success = CachedResource.Success(serverRequestResults, ResponseSource.REMOTE)

        emitNewResultsOrErrorIfEmpty(success)
    }

    /**
     * This function encapsulates the logic for handling empty responses from the data layer.
     * */
    private fun emitNewResultsOrErrorIfEmpty(results: CachedResource.Success<List<Volume>>) {
        if (results.data.isEmpty()) emitError(uiText.noResultsFoundError)
        else emitResults(results)
    }

    private fun emitError(message: String?) {
        searchResultsResourceFlow.value =
            CachedResource.Error(message ?: uiText.unknownErrorMessage)
    }

    private fun emitLoading() {
        searchResultsResourceFlow.value = CachedResource.Loading
    }

    /**
     * This function encapsulates the logic for handling the cases when the string is invalid.
     * @param onValidValue is invoked in case the query string is a valid one.
     * */
    private suspend fun emitLoadingAndValidateQueryStringOrEmitError(
        onValidValue: suspend (query: String) -> Unit,
    ) {
        emitLoading()

        val queryString = queryStringFlow.value.trim().lowercase()

        if (queryString.isNotBlank()) {
            onValidValue(queryString)
        } else {
            emitError(uiText.searchTermNotEnteredErrorMessage)
        }
    }
}
