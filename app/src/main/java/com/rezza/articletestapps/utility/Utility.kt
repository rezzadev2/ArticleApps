package com.rezza.articletestapps.utility

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    @JvmStatic
     fun getDate(pDate: String, inputFormat: String?): Date? {
        var pDate = pDate
        if (pDate.length > 19) {
            pDate = pDate.substring(0, 19)
        }
        val format: DateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        try {
            return format.parse(pDate.replace("T", " ").split("\\.").toTypedArray()[0])
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    @JvmStatic
    fun getDateString(date: Date?, sFormat: String?): String {
        val format: DateFormat = SimpleDateFormat(sFormat, Locale.getDefault())
        return format.format(date!!)
    }
}