package com.rezza.articletestapps.data

import java.io.Serializable

class SourceResponseModel : Serializable {
    var status = ""
    var code = ""
    var message = ""
    var sources = ArrayList<SourceDataModel>()
}