package com.rezza.articletestapps.data

import java.io.Serializable

class ArticlesModel : Serializable {
    var title: String? = null
    var description: String? = null
    var publishedAt: String? = null
    var url: String? = null
    var urlToImage: String? = null
}