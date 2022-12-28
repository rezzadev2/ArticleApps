package com.rezza.articletestapps.entities

import java.io.Serializable
import java.util.*

class News : Serializable {

    var author: String? = null
    var title: String? = null
    var description: String? = null
    var publishedAt: String? = null
    var publishDate: Date? = null
    var url: String? = null
    var urlToImage: String? = null
    var category: String? = null
    var isLoading = false
}