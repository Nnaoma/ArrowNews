package com.virtual.arrownews.api

import com.virtual.arrownews.BuildConfig
import com.virtual.arrownews.data.NewsResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("Authorization: ${BuildConfig.NEWS_ACCESS_KEY}")
    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResult
}