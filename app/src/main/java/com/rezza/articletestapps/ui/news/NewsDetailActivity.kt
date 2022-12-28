package com.rezza.articletestapps.ui.news

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rezza.articletestapps.R
import com.rezza.articletestapps.ui.base.MyActivity
import com.rezza.articletestapps.ui.view.Loading


class NewsDetailActivity : MyActivity() {

    override fun setLayout(): Int {
        return R.layout.news_activity_detail
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initLayout() {
        Loading.showLoading(mActivity, "Please wait...")
        val  url = intent.getStringExtra("url");

        val wbvw_news: WebView = findViewById<View>(R.id.wbvw_news) as WebView
        wbvw_news.settings.loadsImagesAutomatically = true
        wbvw_news.settings.javaScriptEnabled = true
        wbvw_news.settings.domStorageEnabled = true
        wbvw_news.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        if (url != null) {
            wbvw_news.loadUrl(url)
        }
        wbvw_news.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                Loading.cancelLoading()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            Loading.cancelLoading()
        }, 20000)
    }

     override fun initListener() {
        findViewById<View>(R.id.mrly_back).setOnClickListener { view: View? -> onBackPressed() }
    }
}