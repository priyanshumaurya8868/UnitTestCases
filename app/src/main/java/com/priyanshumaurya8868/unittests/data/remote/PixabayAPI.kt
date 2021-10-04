package com.priyanshumaurya8868.unittests.data.remote

import com.priyanshumaurya8868.unittests.BuildConfig
import com.priyanshumaurya8868.unittests.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}