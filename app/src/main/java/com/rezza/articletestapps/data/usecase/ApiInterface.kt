package com.rezza.articletestapps.data.usecase

import retrofit2.http.GET
import retrofit2.http.Url
import com.rezza.articletestapps.data.SourceResponseModel
import com.rezza.articletestapps.data.NewsResponseModel
import retrofit2.Call

interface ApiInterface {
    @GET
    fun getSource(@Url url: String?): Call<SourceResponseModel?>?

    @GET
    fun getNews(@Url url: String?): Call<NewsResponseModel?>?
}