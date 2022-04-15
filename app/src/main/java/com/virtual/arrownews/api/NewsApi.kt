package com.virtual.arrownews.api

import com.virtual.arrownews.BuildConfig
import com.virtual.arrownews.data.NewsResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("x-api-key: ${BuildConfig.NEWS_ACCESS_KEY}")
    @GET("latest_headlines")
    suspend fun getHeadlines(
        @Query("topic") category: String,
        @Query("lang") language: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): NewsResult
}