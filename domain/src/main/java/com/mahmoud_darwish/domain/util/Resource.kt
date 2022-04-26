package com.mahmoud_darwish.domain.util

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading(source: Source = Source.REMOTE) : Resource<Nothing>()
    class Error(val message: String) : Resource<Nothing>()
}

enum class Source { REMOTE, CACHE }
