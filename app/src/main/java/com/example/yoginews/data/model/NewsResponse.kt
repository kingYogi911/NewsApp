package com.example.yoginews.data.model

import com.google.gson.annotations.SerializedName

class NewsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults:Int,
    @SerializedName("articles")
    val articles:ArrayList<NewsModel>
) {
}