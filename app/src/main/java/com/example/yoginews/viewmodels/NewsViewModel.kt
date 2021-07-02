package com.example.yoginews.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.utils.NewsRepository
import com.example.yoginews.views.NewsAdapter
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val newsList = MutableLiveData<ArrayList<NewsModel>>()
    private val _adapter = MutableLiveData<RecyclerView.Adapter<*>>()
    private var chunkCount = 0
    val newsAdapter: LiveData<RecyclerView.Adapter<*>> get() = _adapter

    val loading = MutableLiveData<Int>(View.VISIBLE)
    val contentVisible = MutableLiveData<Int>(View.GONE)
    val errorVisible = MutableLiveData<Int>(View.GONE)
    val errorMessage = MutableLiveData<String>()

    init {
        newsList.value = ArrayList()
        _adapter.value = NewsAdapter(newsList.value!!)
        getNextNewsChunk()
    }

    private fun getNextNewsChunk() {
        viewModelScope.launch {
            contentVisible.value = View.GONE
            loading.value = View.VISIBLE
            repository.getNextNewsChunk().let { list ->
                loading.value = View.GONE
                if (list != null) {
                    chunkCount++
                    newsList.value!!.addAll(list)
                    _adapter.value?.notifyDataSetChanged()
                    contentVisible.value = View.VISIBLE
                } else {
                    errorMessage.value = "Something Went Wrong"
                    errorVisible.value = View.VISIBLE
                }
            }
        }
    }
}