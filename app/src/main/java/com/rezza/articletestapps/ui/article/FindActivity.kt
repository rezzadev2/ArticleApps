package com.rezza.articletestapps.ui.article

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

class FindActivity : MyActivity() {
    private var txvw_find: TextView? = null
    private var edtx_search: EditText? = null
    private var imvw_find: ImageView? = null
    private var shmr_load: ShimmerFrameLayout? = null
    private val listNews: ArrayList<News> = ArrayList<News>()
    private var mSource: Source? = null
    private var mAdapter: NewsAdapter? = null
    private var mainViewModel: NewsViewModel? = null

    override fun setLayout(): Int {
        return R.layout.news_activity_find
    }

    override fun initLayout() {
        txvw_find = findViewById(R.id.txvw_find)
        edtx_search = findViewById(R.id.edtx_search)
        imvw_find = findViewById(R.id.imvw_clear)
        shmr_load = findViewById(R.id.shmr_load)
        imvw_find!!.visibility = View.INVISIBLE
        val rcvw_data: RecyclerView = findViewById(R.id.rcvw_data)
        rcvw_data.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = NewsAdapter(listNews)
        rcvw_data.adapter = mAdapter
        init()
    }

    override fun initListener() {
        mAdapter!!.setOnSelectedListener(object  : NewsAdapter.OnSelectedListener {
            override fun onSelected(data: News?) {
                val intent = Intent(mActivity, NewsDetailActivity::class.java)
                intent.putExtra("url", data?.url)
                startActivity(intent)
            }

        })

        findViewById<MaterialRippleLayout>(R.id.mrly_back).setOnClickListener { onBackPressed() }
        edtx_search?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text: String = editable.toString()
                if (text.isEmpty() && imvw_find!!.visibility == View.VISIBLE) {
                    imvw_find!!.visibility = View.INVISIBLE
                } else if (!text.isEmpty() && imvw_find!!.visibility == View.INVISIBLE) {
                    imvw_find!!.visibility = View.VISIBLE
                }
            }
        })
        imvw_find!!.setOnClickListener { view: View? -> loadData() }
    }

    private fun init() {
        listNews.clear()
        mSource = intent.getSerializableExtra("data") as Source
        val sNote: String = resources.getString(R.string.find_source_from_sport)
            .replace("X1", mSource?.name!!)
        txvw_find?.text = sNote
        mainViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        listNews.clear()
        shmr_load?.visibility = View.VISIBLE
        shmr_load?.startShimmerAnimation()
        txvw_find?.visibility = View.INVISIBLE
        mainViewModel!!.findNews(
            mSource?.id,
            1,
            edtx_search?.text.toString().trim { it <= ' ' })
        mainViewModel!!.getLiveData()
            .observe(this, Observer { newsResponseModel: NewsResponseModel? ->
                if (newsResponseModel == null) {
                    return@Observer
                }
                if (newsResponseModel.code != "ok") {
                    showError(newsResponseModel.message)
                    hideLoading()
                    txvw_find?.text = newsResponseModel.message
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
                hideLoading()
                mAdapter!!.notifyDataSetChanged()
                if (listNews.size == 0) {
                    txvw_find?.visibility = View.VISIBLE
                    val note: String =
                        edtx_search?.text.toString() + " Not found in " + mSource?.name
                    txvw_find?.text = note
                }
            })
    }

    private fun hideLoading() {
        if (shmr_load?.visibility == View.VISIBLE) {
            shmr_load?.visibility = View.GONE
            shmr_load?.stopShimmerAnimation()
        }
    }

    private fun showError(message: String) {
        val dialog = ErrorDialog(mActivity)
        dialog.show("Failed API", message)
    }
}