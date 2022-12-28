package com.rezza.articletestapps.ui.view

import android.app.ProgressDialog
import android.content.Context

@Suppress("DEPRECATION")
object Loading {
    private var g_progressDialog: ProgressDialog? = null
    fun showLoading(p_cContext: Context?, msg: String?) {
        cancelLoading()
        g_progressDialog = ProgressDialog(p_cContext)
        g_progressDialog!!.setTitle(null)
        g_progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        g_progressDialog!!.isIndeterminate = true
        g_progressDialog!!.setMessage(msg)
        g_progressDialog!!.setCancelable(false)
        if (g_progressDialog != null) {
            g_progressDialog!!.show()
        }
    }

    fun cancelLoading() {
        if (g_progressDialog != null) {
            g_progressDialog!!.cancel()
            g_progressDialog = null
        }
    }
}