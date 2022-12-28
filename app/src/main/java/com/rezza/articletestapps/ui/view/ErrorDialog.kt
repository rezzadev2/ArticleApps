package com.rezza.articletestapps.ui.view

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.rezza.articletestapps.R
import com.rezza.articletestapps.ui.base.MyDialog

class ErrorDialog(context: Activity?) : MyDialog(context) {

    private var tv_title: TextView? = null
    private var tv_description: TextView? = null
    private var card_body: CardView? = null

    override fun setLayout(): Int {
        return R.layout.view_dialog_error
    }

    override fun initLayout(view: View?) {
        view!!.findViewById<View>(R.id.mrly_action).setOnClickListener { dismiss() }
        tv_title = view.findViewById(R.id.txvw_title)
        tv_description = view.findViewById(R.id.txvw_description)
        card_body = view.findViewById(R.id.card_body)
        card_body!!.visibility = View.INVISIBLE
    }


    override fun onBackPressed() {
        dismiss()
    }

    fun show(title: String?, description: String?) {
        show()
        tv_title?.text = title
        tv_description?.text = description
        card_body?.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        if (animation != null){
            card_body?.startAnimation(animation)
        }
        else {
            Log.e("TAGRZ","Animation NULL")
        }
    }
}