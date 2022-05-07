package com.mahmoud_darwish.core.util

sealed class CachedResource<out T> {
    class Success<T>(val data: T, val source: Source) : CachedResource<T>()
    object Loading : CachedResource<Nothing>()
    class Error(val message: String) : CachedResource<Nothing>()
}

enum class Source { REMOTE, CACHE }
