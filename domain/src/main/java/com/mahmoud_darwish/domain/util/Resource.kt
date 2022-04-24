package com.mahmoud_darwish.domain.util

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error(val message: String) : Resource<Nothing>()
}