package com.virtual.arrownews.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.virtual.arrownews.data.News
import retrofit2.HttpException
import java.io.IOException

class NewsPagerSource(
    private val category: String,
    private val language: String,
    private val api: NewsApi
) : PagingSource<Int, News>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {

        val page = params.key ?: 1

        return try {
            val headlines = api.getHeadlines(category, language, page, params.loadSize)
            LoadResult.Page(
                data = headlines.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (headlines.articles.isEmpty()) null else page + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}