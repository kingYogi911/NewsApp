package com.example.yoginews.utils

import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsDataSource {
    @GET()
    suspend fun getNews(): Response<NewsResponse>
}