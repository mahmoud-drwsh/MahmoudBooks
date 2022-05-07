@file:Suppress("HardCodedStringLiteral")

package com.mahmoud_darwish.data.remote

// Base URL and the various paths
const val BASE = "https://www.googleapis.com/books/v1/"
const val VOLUMES_PATH = "volumes"

// q parameter
const val Q_PARAM = "q"

// maxResults parameter
const val MAX_RESULTS_PARAM = "maxResults"
const val MAX_RESULTS_PARAM_DEFAULT_ARG = 40

const val ID_PARAM = "id"
const val VOLUMES_PATH_WITH_ID_PARAM = "$VOLUMES_PATH/{${ID_PARAM}}"

// printType parameter
const val PRINT_TYPE_PARAM = "printType"
const val PRINT_TYPE_PARAM_DEFAULT_ARG = "books"

// filter parameter
const val FILTER_PARAM = "filter"
const val FILTER_PARAM_DEFAULT_ARG = "paid-ebooks"

// filter parameter
const val ORDER_BY_PARAM = "orderBy"
const val ORDER_BY_PARAM_DEFAULT_ARG = "relevance"