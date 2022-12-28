package com.rezza.articletestapps.ui.news

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.articletestapps.data.ErrorCode
import com.rezza.articletestapps.data.NewsResponseModel
import com.rezza.articletestapps.data.repository.NewsRepository

class NewsViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: NewsRepository = NewsRepository()
    var liveData: LiveData<NewsResponseModel>

    @SuppressLint("StaticFieldLeak")
    private val application = app.applicationContext
    init {
        liveData = MutableLiveData()
    }

    fun getNews(sourceId: String?, page: Int) {
        if (isNetworkConnected) {
            val data: MutableLiveData<NewsResponseModel> = MutableLiveData<NewsResponseModel>()
            data.postValue(initErrorInternet())
            liveData = data
            return
        }
        liveData = repository.getNewsBySource(sourceId!!, page)!!
    }

    fun findNews(sourceId: String?, page: Int, find: String?) {
        if (isNetworkConnected) {
            val data: MutableLiveData<NewsResponseModel> = MutableLiveData<NewsResponseModel>()
            data.postValue(initErrorInternet())
            liveData = data
            return
        }
        liveData = repository.findNews(sourceId!!, page, find!!)!!
    }

    fun findAllNews(page: Int, find: String?) {
        if (isNetworkConnected) {
            val data: MutableLiveData<NewsResponseModel> = MutableLiveData<NewsResponseModel>()
            data.postValue(initErrorInternet())
            liveData = data
            return
        }
        liveData = repository.findAllNews(page, find!!)!!
    }

    private fun initErrorInternet(): NewsResponseModel {
        val rep = NewsResponseModel()
        rep.code = ErrorCode.INTERNET_PROBLEM.code.toString()
        rep.message = ErrorCode.INTERNET_PROBLEM.message
        return rep
    }

    @JvmName("getLiveData1")
    fun getLiveData(): LiveData<NewsResponseModel> {
        return liveData
    }

    private val isNetworkConnected: Boolean
        get() {
            val cm: ConnectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo()!!.isConnected()
        }
}