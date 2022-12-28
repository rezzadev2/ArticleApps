package com.rezza.articletestapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.articletestapps.data.ApiConfig
import com.rezza.articletestapps.data.CallApiService
import com.rezza.articletestapps.data.ErrorCode
import com.rezza.articletestapps.data.NewsResponseModel
import com.rezza.articletestapps.data.usecase.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    private val apiInterface: ApiInterface
    private var data: MutableLiveData<NewsResponseModel>? = null

    init {
        val callApiService = CallApiService()
        apiInterface = callApiService.client!!.create(ApiInterface::class.java)
    }

    fun getNewsBySource(source: String, page: Int): LiveData<NewsResponseModel>? {
        data = MutableLiveData<NewsResponseModel>()
        val pageSize = 10
        val url: String = (ApiConfig.GET_NEWS +
                "?apiKey=" + ApiConfig.API_KEY
                + "&sources=" + source
                + "&page=" + page
                + "&pageSize=" + pageSize)
        apiInterface.getNews(url)?.enqueue(object : Callback<NewsResponseModel?> {
            override fun onResponse(
                call: Call<NewsResponseModel?>,
                response: Response<NewsResponseModel?>
            ) {
                var rep: NewsResponseModel? = response.body()
                val code: ErrorCode = ErrorCode.map(response.code())
                if (rep == null) {
                    rep = NewsResponseModel()
                    rep.code = code.code.toString()
                    rep.message = code.message
                    data?.postValue(rep)
                    return
                }
                rep.code = code.message
                data?.postValue(rep)
            }

            override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {
                Log.e("HomeRepository", "onFailure")
                val rep = NewsResponseModel()
                rep.code= "failure"
                rep.message = "Unknown response message"
                data?.postValue(rep)
            }
        })
        return data
    }

    fun findNews(source: String, page: Int, find: String): LiveData<NewsResponseModel>? {
        data = MutableLiveData<NewsResponseModel>()
        val pageSize = 50
        val url: String = (ApiConfig.GET_NEWS +
                "?apiKey=" + ApiConfig.API_KEY
                + "&sources=" + source
                + "&q=" + find
                + "&page=" + page
                + "&pageSize=" + pageSize)
        apiInterface.getNews(url)?.enqueue(object : Callback<NewsResponseModel?> {
            override fun onResponse(
                call: Call<NewsResponseModel?>,
                response: Response<NewsResponseModel?>
            ) {
                var rep: NewsResponseModel? = response.body()
                val code: ErrorCode = ErrorCode.map(response.code())
                if (rep == null) {
                    rep = NewsResponseModel()
                    rep.code = code.code.toString()
                    rep.message = code.message
                    data?.postValue(rep)
                    return
                }
                rep.code = code.message
                data?.postValue(rep)
            }

            override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {
                Log.e("HomeRepository", "onFailure")
                val rep = NewsResponseModel()
                rep.code= "failure"
                rep.message = "Unknown response message"
                data?.postValue(rep)
            }
        })
        return data
    }

    fun findAllNews(page: Int, find: String): LiveData<NewsResponseModel>? {
        data = MutableLiveData<NewsResponseModel>()
        val pageSize = 30
        val url: String = (ApiConfig.GET_NEWS +
                "?apiKey=" + ApiConfig.API_KEY
                + "&q=" + find
                + "&page=" + page
                + "&pageSize=" + pageSize)
        apiInterface.getNews(url)?.enqueue(object : Callback<NewsResponseModel?> {
            override fun onResponse(
                call: Call<NewsResponseModel?>,
                response: Response<NewsResponseModel?>
            ) {
                var rep: NewsResponseModel? = response.body()
                val code: ErrorCode = ErrorCode.map(response.code())
                if (rep == null) {
                    rep = NewsResponseModel()
                    rep.code = code.code.toString()
                    rep.message = code.message
                    data?.postValue(rep)
                    return
                }
                rep.code = code.message;
                data?.postValue(rep)
            }

            override fun onFailure(call: Call<NewsResponseModel?>, t: Throwable) {
                Log.e("HomeRepository", "onFailure")
                val rep = NewsResponseModel()
                rep.code= "failure"
                rep.message = "Unknown response message"
                data?.postValue(rep)
            }
        })
        return data
    }
}