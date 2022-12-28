package com.rezza.articletestapps.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.rezza.articletestapps.R
import com.rezza.articletestapps.ui.base.MyActivity
import com.rezza.articletestapps.ui.category.CategoryActivity


@SuppressLint("CustomSplashScreen")
class MainSplashActivity : MyActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initLayout() {
        android.os.Handler().postDelayed({
            startActivity(Intent(this, CategoryActivity::class.java))
            this.finish()
        }, 2000)
    }

    override fun initListener() {}
}