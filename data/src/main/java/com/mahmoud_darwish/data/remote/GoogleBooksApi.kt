package com.mahmoud_darwish.data.remote

import com.mahmoud_darwish.data.remote.model.VolumeDto
import com.mahmoud_darwish.data.remote.model.VolumeSearchDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
        @Query(FILTER_PARAM) filter: String = FILTER_PARAM_DEFAULT_ARG
    ): VolumeSearchDto

    @GET("$VOLUMES_PATH/{id}")
    suspend fun volume(
        @Path("id") id: String
    ): VolumeDto
}

/**
 * This interceptor adds the API key to the requests
 * */
private val apiKeyInterceptor: (Interceptor.Chain) -> Response = {
    val originalRequest = it.request()

    val newUrl = originalRequest.url().newBuilder()
        .addQueryParameter("app_key", API_KEY)
        .build()

    val build = originalRequest
        .newBuilder()
        .url(newUrl)
        .build()

    println("Requesting: ${newUrl.url()}")

    it.proceed(build)
}


private val okHttpClient: OkHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(apiKeyInterceptor)
    .build()

/**
 * This function will be used by the DI lib for creating a single instance of the service which will be the one used while the app is running
 * */
fun getBooksServiceInstance(): GoogleBooksApi = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .baseUrl(BASE)
    .build()
    .create(GoogleBooksApi::class.java)
