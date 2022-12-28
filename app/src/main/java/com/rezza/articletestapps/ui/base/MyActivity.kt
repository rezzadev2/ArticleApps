package com.rezza.articletestapps.ui.base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class MyActivity : AppCompatActivity() {

    protected var mActivity : Activity ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this;
        setContentView(setLayout())
        initLayout()
        initListener()
    }

    protected abstract fun setLayout(): Int
    protected abstract fun initLayout()
    protected abstract fun initListener()
}