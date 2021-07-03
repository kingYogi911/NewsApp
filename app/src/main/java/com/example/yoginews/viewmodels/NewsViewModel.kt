package com.example.yoginews.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.data.model.PECModel
import com.example.yoginews.utils.NewsRepository
import com.example.yoginews.views.NewsAdapter
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val newsList = MutableLiveData<ArrayList<NewsModel>>()
    private val _adapter = MutableLiveData<NewsAdapter>()
    var isConnected = MutableLiveData<Boolean>(false)
    val newsAdapter: LiveData<NewsAdapter> get() = _adapter
    val controller = PECModel()
    private var lastNetStatus = false

    init {
        Log.d(TAG, "INIT")
        isConnected.observeForever {
            it?.let {
                if (lastNetStatus != it) {
                    lastNetStatus = it
                    if (it) {
                        newsList.value = ArrayList()
                        _adapter.value = NewsAdapter(newsList.value!!)
                        getNextNewsChunk()
                    } else {
                        controller.apply {
                            progress.value = View.GONE
                            msg.value = "No Internet!"
                            error.value = View.VISIBLE
                            content.value = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun getNextNewsChunk() {
        if (isConnected.value == true) {
            viewModelScope.launch {
                controller.apply {
                    content.value = View.GONE
                    progress.value = View.VISIBLE
                }
                repository.getNextNewsChunk().let { list ->
                    controller.progress.value = View.GONE
                    if (list != null) {
                        newsList.value!!.addAll(list)
                        _adapter.value?.notifyDataSetChanged()
                        controller.content.value = View.VISIBLE
                    } else {
                        controller.apply {
                            msg.value = "Something Went Wrong"
                            error.value = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "NewsViewModel"
    }
}