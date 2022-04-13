package com.virtual.arrownews.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class NewsRepository @Inject constructor(private val api: NewsApi) {

    fun getHeadlineCategory(
        category: String = "general",
        language: String = "en"
    ) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            initialLoadSize = 30,
        ), pagingSourceFactory = { NewsPagerSource(api = api, category = category, language = language) }
    ).liveData
}