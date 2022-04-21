package com.mahmoud_darwish.api_service

import com.mahmoud_darwish.api_service.model.VolumesSearchResult
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET(VOLUMES_PATH)
    suspend fun search(
        @Query(Q_PARAM) q: String,
        @Query(Q_MAX_RESULTS) maxResults: Int = Q_MAX_RESULTS_DEFAULT_ARG,
        @Query(Q_PRINT_TYPE) printType: String = Q_PRINT_TYPE_DEFAULT_ARG
    ): VolumesSearchResult

    @GET("$VOLUMES_PATH/{id}")
    suspend fun volume(
        @Path("id") id: String
    ): VolumesSearchResult.Volume
}

/**
 * This function will be used by Hilt for creating a single instance of the service which will be the one used while the app is running
 * */
fun getInstance(): Service {
    val apiKeyInterceptor: (Interceptor.Chain) -> Response = {
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
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor).build()

    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            okHttpClient
        )
        .baseUrl(BASE)
        .build()
        .create(Service::class.java)
}