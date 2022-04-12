package com.virtual.arrownews.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.virtual.arrownews.api.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    fun getHeadlines() = repository.getHeadlineCategory().cachedIn(viewModelScope)

}