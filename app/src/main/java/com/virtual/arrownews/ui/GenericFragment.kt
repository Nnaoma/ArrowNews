package com.virtual.arrownews.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.virtual.arrownews.R
import com.virtual.arrownews.RefreshAdapter
import com.virtual.arrownews.adapters.NewsSourceAdapter
import com.virtual.arrownews.adapters.NewsSourceLoadStateAdapter
import com.virtual.arrownews.databinding.FragmentHeadlinesBinding
import com.virtual.arrownews.models.HeadlinesViewModel

open class GenericFragment : Fragment(R.layout.fragment_headlines), RefreshAdapter {

    private val viewModel by viewModels<HeadlinesViewModel>()
    private var headlinesFragmentBinding: FragmentHeadlinesBinding? = null

    private lateinit var initialAdapter: NewsSourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialAdapter = NewsSourceAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headlinesFragmentBinding = FragmentHeadlinesBinding.bind(view)

        initialAdapter.addLoadStateListener {  loadState ->
            headlinesFragmentBinding?.apply {
                loadingProgressBar.visibility = if(loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                recyclerView.visibility = if(loadState.source.refresh is LoadState.NotLoading) View.VISIBLE else View.INVISIBLE
                errorText.visibility = if(loadState.source.refresh is LoadState.Error) View.VISIBLE else View.GONE

                // empty result
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    initialAdapter.itemCount < 1) {
                    recyclerView.visibility = View.INVISIBLE
                    emptyText.visibility = View.VISIBLE
                } else {
                    emptyText.visibility = View.GONE
                }
            }

        }

        headlinesFragmentBinding?.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = initialAdapter.withLoadStateFooter(NewsSourceLoadStateAdapter{
                initialAdapter.retry()
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        headlinesFragmentBinding = null
    }

    override fun refreshAdapter() {
        val adapter = headlinesFragmentBinding?.recyclerView?.adapter
        if (adapter != null && adapter is ConcatAdapter)
            initialAdapter.refresh()
    }

    fun observeHeadline(category: String, language: String = "en"){
        viewModel.getHeadlines(category = category, language = language).observe(viewLifecycleOwner) {
            initialAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}