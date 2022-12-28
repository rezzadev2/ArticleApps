package com.rezza.articletestapps.data

import java.io.Serializable

class NewsResponseModel : Serializable {
    var status: String? = null
    var totalResults = 0
    var code = ""
    var message = ""
    var articles = ArrayList<ArticlesModel>()
}