package com.example.yoginews.data.model

class NewsModel(
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("urlToImage")
    val image: String,
    @SerializedName("url")
    val webLink: String?
) {
}