package com.example.yoginews.data.model

class NewsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults:Int,
    @SerializedName("articles"),
    val articles:ArrayList<NewsModel>
) {
}