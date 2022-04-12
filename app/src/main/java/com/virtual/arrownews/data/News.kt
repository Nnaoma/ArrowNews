package com.virtual.arrownews.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val publishedAt: String,
    val description: String,
    val urlToImage: String?,
    val url: String,
    val title: String?,
    val source: Source
) : Parcelable {
    @Parcelize
    data class Source(val name: String) : Parcelable
}
