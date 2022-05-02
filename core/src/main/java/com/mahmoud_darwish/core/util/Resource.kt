package com.mahmoud_darwish.core.util

sealed class Resource<out T> {
    class Success<T>(val data: T, val source: Source) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error(val message: String) : Resource<Nothing>()
}

enum class Source { REMOTE, CACHE }
