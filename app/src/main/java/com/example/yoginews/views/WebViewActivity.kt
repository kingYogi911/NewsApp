package com.example.yoginews.views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.yoginews.data.model.PECModel
import com.example.yoginews.databinding.ActivityWebViewBinding
import com.example.yoginews.utils.MyHelper
import com.example.yoginews.utils.StaticVariables.Companion.WEB_URL
import java.net.URL

class WebViewActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webUrl = intent.extras!!.getString(WEB_URL)
        val url = URL(webUrl)
        val cntrlr = PECModel()
        ActivityWebViewBinding.inflate(layoutInflater).apply {
            this.controller = cntrlr
            this.lifecycleOwner = this@WebViewActivity
            this.webView.settings.javaScriptEnabled = true
            this.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    url: String
                ): Boolean {
                    view.loadUrl(url)
                    cntrlr.progress.value = View.VISIBLE
                    return true
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    cntrlr.apply {
                        progress.value = View.GONE
                        content.value = View.VISIBLE
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    cntrlr.apply{
                        progress.value=View.GONE
                        msg.value = "$description"
                        error.value = View.VISIBLE
                    }
                }
            }

        }.also {
            MyHelper(this@WebViewActivity).observe(this@WebViewActivity, Observer { state ->
                if (state == true) {
                    it.webView.loadUrl(url.toString())
                } else {
                    cntrlr.apply {
                        progress.value = View.GONE
                        content.value = View.GONE
                        error.value = View.VISIBLE
                        msg.value = "No Internet!"
                    }
                }
            })
            setContentView(it.root)
            setSupportActionBar(it.toolbarLayout as Toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }
}