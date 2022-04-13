package com.virtual.arrownews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.virtual.arrownews.databinding.LoadStateLayoutBinding

class NewsSourceLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<NewsSourceLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.refreshButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(state: LoadState){
            binding.apply {
                loadingProgressBar.visibility = if(state is LoadState.Loading) View.VISIBLE else View.GONE
                errorText.visibility = if(state is LoadState.Error) View.VISIBLE else View.GONE
                refreshButton.visibility = if(state is LoadState.Error) View.VISIBLE else View.GONE
            }
        }
    }
}