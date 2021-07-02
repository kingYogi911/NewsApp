package com.example.yoginews.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yoginews.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityWebViewBinding.inflate(layoutInflater).apply {

        }.also {
            setContentView(it.root)
        }
    }
}