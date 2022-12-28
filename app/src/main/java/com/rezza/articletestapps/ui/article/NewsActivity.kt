package com.rezza.articletestapps.ui.article

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.data.NewsResponseModel
import com.rezza.articletestapps.entities.News
import com.rezza.articletestapps.entities.Source
import com.rezza.articletestapps.ui.base.MyActivity
import com.rezza.articletestapps.ui.view.ErrorDialog
import com.rezza.articletestapps.utility.Utility
import java.util.*

class NewsActivity : MyActivity() {
    private var txvw_header: TextView? = null
    private var rcvw_news: RecyclerView? = null
    private var shmr_load: ShimmerFrameLayout? = null
    private val listNews: ArrayList<News> = ArrayList<News>()
    private var mAdapter: NewsAdapter? = null
    private var mSource: Source? = null
    private var mViewModel: NewsViewModel? = null
    private var mPage = 1
    private var isLoading = false

    override fun setLayout(): Int {
        return R.layout.news_activity
    }

    override fun initLayout() {
        txvw_header = findViewById(R.id.txvw_header)
        shmr_load = findViewById(R.id.shmr_load)
        rcvw_news = findViewById(R.id.rcvw_news)
        rcvw_news?.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = NewsAdapter(listNews)
        rcvw_news?.adapter = mAdapter
        init()
    }

    override fun initListener() {
        findViewById<MaterialRippleLayout>(R.id.mrly_back).setOnClickListener { onBackPressed() }
        findViewById<MaterialRippleLayout>(R.id.mrly_search).setOnClickListener {
            val intent = Intent(mActivity, FindActivity::class.java)
            intent.putExtra("data", mSource)
            startActivity(intent)
        }
        rcvw_news?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager: LinearLayoutManager =
                    rcvw_news!!.layoutManager as LinearLayoutManager
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == listNews.size - 1) {
                        val news = News()
                        news.isLoading = true
                        listNews.add(news)
                        mAdapter!!.notifyItemInserted(listNews.size)
                        loadData()
                        isLoading = true
                    }
                }
            }
        })
        mAdapter!!.setOnSelectedListener(object  : NewsAdapter.OnSelectedListener {
            override fun onSelected(data: News?) {
                val intent = Intent(mActivity, NewsDetailActivity::class.java)
                if (data != null) {
                    intent.putExtra("url", data.url)
                }
                startActivity(intent)
            }

        })
    }

    private fun init() {
        mSource = intent.getSerializableExtra("data") as Source
        txvw_header?.text = mSource!!.name
        mViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        shmr_load?.visibility = View.VISIBLE
        shmr_load?.startShimmerAnimation()
        loadData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        mViewModel!!.getNews(mSource?.id, mPage)
        mViewModel!!.getLiveData().observe(this, Observer { newsResponseModel: NewsResponseModel? ->
            if (listNews.size > 0) {
                listNews.removeAt(listNews.size - 1)
                mAdapter!!.notifyItemRemoved(listNews.size)
            }
            if (newsResponseModel == null) {
                hideLoading()
                return@Observer
            }
            if (newsResponseModel.code != "ok") {
                showError(newsResponseModel.message)
                hideLoading()
                return@Observer
            }
            for (article in newsResponseModel.articles) {
                val news = News()
                news.category = mSource?.category
                news.author = mSource?.name
                news.title = article.title
                news.url = article.url
                news.urlToImage = article.urlToImage
                news.description = article.description
                val date: Date? = Utility.getDate( article.publishedAt!!,"yyyy-MM-dd HH:mm:ss" ) //"2022-12-23T02:11:00+00:00
                news.publishedAt = Utility.getDateString(date, "EEEE, dd MMMM yyyy HH:mm")
                news.publishDate = date
                listNews.add(news)
            }
            if (newsResponseModel.totalResults > listNews.size) {
                mPage++
                isLoading = false
            }
            hideLoading()
            mAdapter!!.notifyDataSetChanged()
        })
    }

    private fun showError(message: String) {
        val dialog = ErrorDialog(mActivity)
        dialog.show("Failed API", message)
    }

    private fun hideLoading() {
        if (shmr_load?.visibility == View.VISIBLE) {
            shmr_load?.visibility = View.GONE
            shmr_load?.stopShimmerAnimation()
        }
    }
}