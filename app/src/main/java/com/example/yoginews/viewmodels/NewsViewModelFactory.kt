package com.example.yoginews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yoginews.utils.NewsDataSource
import com.example.yoginews.utils.NewsRepository
import com.example.yoginews.utils.StaticVariables
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InvalidClassException

class NewsViewModelFactory(private val repos: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == NewsViewModel::class.java)
            return NewsViewModel(
                repos
            ) as T
        else throw InvalidClassException("View Model Not Found")
    }

    companion object {
        private var instance: NewsViewModelFactory? = null
        fun getNewInstance(): NewsViewModelFactory {
            return if (instance == null) {
                val interceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                Retrofit.Builder()
                    .baseUrl(StaticVariables.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(NewsDataSource::class.java).let { dataSource ->
                        NewsViewModelFactory(NewsRepository(dataSource)).also { factory ->
                            instance = factory
                        }
                    }
            } else {
                instance!!
            }
        }
    }
}