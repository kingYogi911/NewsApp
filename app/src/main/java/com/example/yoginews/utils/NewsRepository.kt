package com.example.yoginews.utils

import android.util.Log
import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.utils.StaticVariables.Companion.API_KEY

class NewsRepository(private val source: NewsDataSource) {
    suspend fun getNextNewsChunk(): List<NewsModel>? {
        return try {
            source.getNews(
                q="tesla",
                from="2021-06-03",
                sortBy="publishedAt",
                apiKey=API_KEY
            ).let { response ->
                Log.d(TAG, "response.isSuccessful${response.isSuccessful}")
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

    companion object {
        const val TAG = "NewsRepository"
    }
}