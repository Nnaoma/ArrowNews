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

    fun getHeadlineCategory() = Pager(
        config = PagingConfig(
            pageSize = 15,
            maxSize = 50,
        ), initialKey = 1, pagingSourceFactory = { NewsPagerSource(api = api) }
    ).liveData
}