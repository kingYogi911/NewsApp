package com.example.yoginews.utils

import com.example.yoginews.data.model.NewsModel

class NewsRepository(private val source: NewsDataSource) {
    suspend fun getNextNewsChunk(): List<NewsModel>? {
        return try {
            source.getNews().let { response ->
                if (response.isSuccessful) {
                    if (response.body()!!.status == "ok") {
                        response.body()!!.articles
                    } else {
                        //error at serve side
                        //possible error as per api the from=date value is causing
                        null
                    }
                } else {
                    //request is not full filed by server
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}