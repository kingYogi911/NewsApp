package com.example.yoginews.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yoginews.databinding.ActivityWebViewBinding
import com.example.yoginews.utils.StaticVariables.Companion.WEB_URL
import java.net.URL

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webUrl = intent.extras!!.getString(WEB_URL)
        val url = URL(webUrl)
        ActivityWebViewBinding.inflate(layoutInflater).apply {
            this.webView.settings.javaScriptEnabled=true
            this.webView.loadUrl(url.toString())
        }.also {
            setContentView(it.root)
        }
    }
}