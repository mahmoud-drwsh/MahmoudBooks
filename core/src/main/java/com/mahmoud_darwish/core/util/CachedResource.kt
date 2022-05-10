package com.mahmoud_darwish.core.util

/**
 * This sealed class is used to represent the various responses from the data layer to the UI layer.
 * */
sealed class CachedResource<out T> {
    /**
     * @param responseSource specifies the responseSource of the data contained in this class instance.
     * */
    class Success<T>(val data: T, val responseSource: ResponseSource) : CachedResource<T>()
    object Loading : CachedResource<Nothing>()
    class Error(val errorMessage: String) : CachedResource<Nothing>()
}

enum class ResponseSource { REMOTE, CACHE }
