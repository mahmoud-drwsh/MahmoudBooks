package com.mahmoud_darwish.data.remote

import com.mahmoud_darwish.data.remote.model.VolumeDto
import com.mahmoud_darwish.data.remote.model.VolumeSearchDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET(VOLUMES_PATH)
    suspend fun search(
        @Query(Q_PARAM) q: String,
        @Query(MAX_RESULTS_PARAM) maxResults: Int = MAX_RESULTS_PARAM_DEFAULT_ARG,
        @Query(PRINT_TYPE_PARAM) printType: String = PRINT_TYPE_PARAM_DEFAULT_ARG,
        @Query(FILTER_PARAM) filter: String = FILTER_PARAM_DEFAULT_ARG,
        @Query(ORDER_BY_PARAM) orderBy: String = ORDER_BY_PARAM_DEFAULT_ARG
    ): VolumeSearchDto

    @GET(VOLUMES_PATH_WITH_ID_PARAM)
    suspend fun volume(
        @Path(ID_PARAM) id: String
    ): VolumeDto
}

/**
 * This function will be used by the DI lib for creating a single instance of the service which will be the one used while the app is running
 * */
fun getBooksServiceInstance(): GoogleBooksApi = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE)
    .build()
    .create(GoogleBooksApi::class.java)
