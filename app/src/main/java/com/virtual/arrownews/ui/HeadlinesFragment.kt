package com.virtual.arrownews.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.virtual.arrownews.R
import com.virtual.arrownews.adapters.NewsSourceAdapter
import com.virtual.arrownews.databinding.FragmentHeadlinesBinding
import com.virtual.arrownews.models.HeadlinesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeadlinesFragment : Fragment(R.layout.fragment_headlines) {

    private val viewModel by viewModels<HeadlinesViewModel>()
    private var headlinesFragmentBinding: FragmentHeadlinesBinding? = null

    companion object {
        @JvmStatic
        fun newInstance() = HeadlinesFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headlinesFragmentBinding = FragmentHeadlinesBinding.bind(view)
        val recyclerAdapter = NewsSourceAdapter()

        headlinesFragmentBinding?.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = recyclerAdapter
        }

        viewModel.getHeadlines().observe(viewLifecycleOwner) {
            recyclerAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        headlinesFragmentBinding = null
    }
}