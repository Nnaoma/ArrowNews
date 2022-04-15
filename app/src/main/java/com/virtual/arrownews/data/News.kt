package com.virtual.arrownews.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val _id: String,
    @SerializedName("excerpt")
    val description: String,
    @SerializedName("media")
    val urlToImage: String?,
    val link: String,
    val title: String?,
    val source: Source
) : Parcelable {
    @Parcelize
    data class Source(@SerializedName("rights") val name: String?) : Parcelable
}
