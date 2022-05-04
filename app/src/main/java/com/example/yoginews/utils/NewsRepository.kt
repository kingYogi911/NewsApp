package com.example.yoginews.utils

import android.util.Log
import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.utils.StaticVariables.Companion.API_KEY
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsRepository @Inject constructor(private val source: NewsDataSource) {
    suspend fun getNextNewsChunk(query: String = "yogi"): List<NewsModel>? {
        return try {
            val date =
                SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Calendar.getInstance().let {
                    it.add(Calendar.DAY_OF_WEEK, -1)
                    it.time
                })
            source.getNews(
                q = query,
                from = date,
                sortBy = "publishedAt",
                apiKey = API_KEY
            ).let { response ->
                Log.d(TAG, "response.isSuccessful:${response.isSuccessful}")
                if (response.isSuccessful) {
                    Log.d(TAG, response.body()!!.status)
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