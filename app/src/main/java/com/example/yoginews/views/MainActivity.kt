package com.example.yoginews.views

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.yoginews.R
import com.example.yoginews.databinding.ActivityMainBinding
import com.example.yoginews.utils.NewsDataSource
import com.example.yoginews.utils.StaticVariables.Companion.BASE_URL
import com.example.yoginews.viewmodels.NewsViewModel
import com.example.yoginews.viewmodels.NewsViewModelFactory
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel =NewsViewModelFactory.getNewInstance().let {factory->
            ViewModelProvider(this,factory).get(NewsViewModel::class.java)
        }
        ActivityMainBinding.inflate(layoutInflater).apply {
            this.viewmodel=viewModel
            this.lifecycleOwner = this@MainActivity
        }.also {
            setContentView(it.root)
        }
    }
}