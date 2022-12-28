package com.rezza.articletestapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.articletestapps.data.ApiConfig
import com.rezza.articletestapps.data.CallApiService
import com.rezza.articletestapps.data.ErrorCode
import com.rezza.articletestapps.data.SourceResponseModel
import com.rezza.articletestapps.data.usecase.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceRepository {
    private val apiInterface: ApiInterface
    private var data: MutableLiveData<SourceResponseModel>? = null

    init {
        val callApiService = CallApiService()
        apiInterface = callApiService.client!!.create(ApiInterface::class.java)
    }

    fun getSourceByCategory(category: String): LiveData<SourceResponseModel>? {
        data = MutableLiveData<SourceResponseModel>()
        val url: String = ApiConfig.GET_SOURCE + "?apiKey=" + ApiConfig.API_KEY + "&category=" + category

        Log.d("SourceRepository", "API $url")
        apiInterface.getSource(url)?.enqueue(object : Callback<SourceResponseModel?> {
            override fun onResponse(
                call: Call<SourceResponseModel?>,
                response: Response<SourceResponseModel?>
            ) {
                var rep: SourceResponseModel? = response.body()
                val code: ErrorCode = ErrorCode.map(response.code())

                if (rep != null) {
                    Log.d("SourceRepository", "code $code")
                    rep.code =code.message
                    rep.message = code.message
                    data?.postValue(rep)
                } else {
                    Log.e("SourceRepository", "response " + response.code())
                    rep = SourceResponseModel()
                    rep.code = code.code.toString()
                    rep.message = code.message
                    data?.postValue(rep)
                }
            }

            override fun onFailure(call: Call<SourceResponseModel?>, t: Throwable) {
                Log.e("SourceRepository", "onFailure")
                val rep = SourceResponseModel()
                rep.code= "failure"
                rep.message = "Unknown response message"
                data?.postValue(rep)
            }
        })
        return data
    }
}