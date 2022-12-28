package com.rezza.articletestapps.ui.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.rezza.articletestapps.R
import java.util.*

abstract class MyDialog(context: Activity?) : Dialog(context!!, R.style.AppTheme_transparent) {


    init {
        val window = this.window
        val wlmp: WindowManager.LayoutParams = window!!.attributes
        wlmp.gravity = Gravity.CENTER_HORIZONTAL
        window.attributes = wlmp
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        this.setTitle(null)
        this.setCancelable(false)
        this.setOnCancelListener(null)
        val view: View = LayoutInflater.from(context).inflate(this.setLayout(), null)
        this.setContentView(view)
        setTransparent()
        this.initLayout(view)
    }

    private fun setTransparent() {
        window!!.statusBarColor = Color.parseColor("#00ffffff")

    }

    protected abstract fun setLayout(): Int
    protected abstract fun initLayout(view: View?)
}