package com.mahmoud_darwish.domain.util

sealed class Resource<out T> {
    class Success<T>(val data: T, source: Source, message: String? = null) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error(val message: String) : Resource<Nothing>()
}

enum class Source { REMOTE, CACHE }
