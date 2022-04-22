package com.mahmoud_darwish.domain.util

sealed class Resource<T> {
    class Success<T>(data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
    class Error(message: String) : Resource<Nothing>()
}