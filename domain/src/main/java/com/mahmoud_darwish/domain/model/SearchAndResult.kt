package com.mahmoud_darwish.domain.model

data class SearchAndResult<T>(
    val query: String,
    val result: T
)