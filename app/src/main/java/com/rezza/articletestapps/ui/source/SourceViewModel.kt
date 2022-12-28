package com.rezza.articletestapps.ui.source

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rezza.articletestapps.data.ErrorCode
import com.rezza.articletestapps.data.SourceResponseModel
import com.rezza.articletestapps.data.repository.SourceRepository

@Suppress("DEPRECATION")
class SourceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SourceRepository = SourceRepository()
    private var liveData: LiveData<SourceResponseModel>

    @SuppressLint("StaticFieldLeak")
    private val application = application.applicationContext

    init {
        liveData = MutableLiveData()
    }

    fun getSource(category: String?) {
        if (isNotConnected) {
            val data: MutableLiveData<SourceResponseModel> = MutableLiveData<SourceResponseModel>()
            data.postValue(initErrorInternet())
            liveData = data
        }
        else {
            liveData = repository.getSourceByCategory(category!!)!!
        }

    }

    fun getLiveData(): LiveData<SourceResponseModel> {
        return liveData
    }

    private fun initErrorInternet(): SourceResponseModel {
        val rep = SourceResponseModel()
        rep.code = ErrorCode.INTERNET_PROBLEM.code.toString()
        rep.message = ErrorCode.INTERNET_PROBLEM.message
        return rep
    }


    private val isNotConnected: Boolean
        get() {
            val cm: ConnectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo == null || !cm.activeNetworkInfo!!.isConnected
        }

}