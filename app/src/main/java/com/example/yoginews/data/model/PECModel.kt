package com.example.yoginews.data.model

import android.view.View
import androidx.lifecycle.MutableLiveData

class PECModel {
    val progress=MutableLiveData<Int>(View.VISIBLE)
    val content=MutableLiveData<Int>(View.GONE)
    val error=MutableLiveData<Int>(View.GONE)
    val msg=MutableLiveData<String>("")
}