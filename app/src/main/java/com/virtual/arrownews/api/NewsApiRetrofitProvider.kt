package com.virtual.arrownews.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsApiRetrofitProvider {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit) : NewsApi = retrofit.create(NewsApi::class.java)
}