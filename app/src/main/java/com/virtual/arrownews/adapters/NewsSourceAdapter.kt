package com.virtual.arrownews.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.virtual.arrownews.DisplayActivity
import com.virtual.arrownews.R
import com.virtual.arrownews.data.News
import com.virtual.arrownews.databinding.AlternateNewsOutlineLayoutBinding
import com.virtual.arrownews.databinding.MainNewsOutlineLayoutBinding
import com.virtual.arrownews.ui.screens.others.WEB_URL

class NewsSourceAdapter :
    PagingDataAdapter<News, RecyclerView.ViewHolder>(diffCallback = diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = AlternateNewsOutlineLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AlternateNewsOutlineViewHolder(binding)
        } else {
            val binding = MainNewsOutlineLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MainNewsOutlineViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null && holder is MainNewsOutlineViewHolder)
            holder.bind(item)
        else if (item != null && holder is AlternateNewsOutlineViewHolder)
            holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) 0 else 1
    }

    inner class MainNewsOutlineViewHolder(private val binding: MainNewsOutlineLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                val item = getItem(position)

                if (item != null){
                    val intent = Intent(binding.root.context, DisplayActivity::class.java).apply {
                        action = DisplayActivity.WEB_VIEW_FRAGMENT
                        putExtra(WEB_URL, item.link)
                    }
                    binding.root.context.startActivity(intent)
                }
            }
        }
        fun bind(news: News) {
            binding.apply {
                description.text = news.description
                sourceName.text = news.name
                Glide.with(root).load(news.urlToImage).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.error_24)
                    .into(imageview)
            }
        }
    }

    inner class AlternateNewsOutlineViewHolder(private val binding: AlternateNewsOutlineLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                val item = getItem(position)

                if (item != null){
                    val intent = Intent(binding.root.context, DisplayActivity::class.java).apply {
                        action = DisplayActivity.WEB_VIEW_FRAGMENT
                        putExtra(WEB_URL, item.link)
                    }
                    binding.root.context.startActivity(intent)
                }
            }
        }

        fun bind(news: News) {
            binding.apply {
                description.text = news.description
                sourceName.text = news.name
                Glide.with(root).load(news.urlToImage).centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imageview)
            }
        }
    }
}