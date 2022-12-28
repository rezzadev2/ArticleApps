package com.rezza.articletestapps.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CallApiService {
    private var mRetrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.readTimeout(100, TimeUnit.SECONDS)
            clientBuilder.connectTimeout(100, TimeUnit.SECONDS)
            val okHttpClient = clientBuilder.build()
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .baseUrl(ApiConfig.API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return mRetrofit
        }
}