package com.mahmoud_darwish.api_service

import com.mahmoud_darwish.api_service.model.VolumesSearchResult
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE = "https://www.googleapis.com/books/v1/"
const val VOLUMES_PATH = "volumes"

const val Q_PARAM = "q"
const val Q_MAX_RESULTS = "maxResults"
const val Q_MAX_RESULTS_DEFAULT_ARG = 40

const val Q_PRINT_TYPE = "printType"
const val Q_PRINT_TYPE_DEFAULT_ARG = "books"

interface Service {
    @GET(VOLUMES_PATH)
    suspend fun search(
        @Query(Q_PARAM) q: String,
        @Query(Q_MAX_RESULTS) maxResults: Int = Q_MAX_RESULTS_DEFAULT_ARG,
        @Query(Q_PRINT_TYPE) printType: String = Q_PRINT_TYPE_DEFAULT_ARG
    ): VolumesSearchResult

    @GET("${VOLUMES_PATH}/{id}")
    suspend fun volume(
        @Path("id") id: String
    ): VolumesSearchResult.Volume
}

/**
 * This function will be used by Hilt for creating a single instance of the service which will be the one used while the app is running
 * */
fun getInstance(): Service = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE)
    .build()
    .create(Service::class.java)