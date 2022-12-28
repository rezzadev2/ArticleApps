package com.rezza.articletestapps.ui.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.balysv.materialripple.MaterialRippleLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.ui.base.MyView

class SearchView(context: Context?, attrs: AttributeSet?) : MyView(context, attrs) {
    private var tv_search: TextView? = null
    private var et_search: EditText? = null
    private var im_clear: ImageView? = null
    private var mrly_serach: MaterialRippleLayout? = null
    private var onActionListener: OnActionListener? = null
    private var onSearchListener: OnSearchListener? = null
    override fun setlayout(): Int {
        return R.layout.view_search
    }

    override fun initLayout() {
        tv_search = findViewById(R.id.txvw_search)
        mrly_serach = findViewById(R.id.mrly_serach)
        et_search = findViewById(R.id.edtx_search)
        im_clear = findViewById(R.id.imvw_clear)
        et_search!!.visibility = GONE
        im_clear!!.visibility = GONE
        tv_search!!.visibility = VISIBLE
    }

    override fun initListener() {
        mrly_serach!!.setOnClickListener { view: View? ->
            if (onActionListener != null) {
                onActionListener!!.onAction()
            }
        }
        et_search!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text = editable.toString().trim { it <= ' ' }
                if (text.isEmpty() && im_clear!!.visibility == View.VISIBLE) {
                    im_clear!!.visibility = View.GONE
                } else if (!text.isEmpty() && im_clear!!.visibility == View.GONE) {
                    im_clear!!.visibility = View.VISIBLE
                }
                if (onSearchListener != null) {
                    onSearchListener!!.onSearch(text)
                }
            }
        })
        im_clear!!.setOnClickListener { view: View? -> et_search?.text = null }
    }

    fun create(title: String?) {
        super.create()
        tv_search!!.text = title
    }

    fun createWithTyping(hint: String?) {
        super.create()
        et_search!!.visibility = VISIBLE
        tv_search!!.visibility = GONE
        mrly_serach!!.visibility = GONE
        et_search!!.hint = hint
    }

    fun setOnActionListener(listener: OnActionListener?) {
        onActionListener = listener
    }

    interface OnActionListener {
        fun onAction()
    }

    fun setonSearchListener(onSearchListener: OnSearchListener?) {
        this.onSearchListener = onSearchListener
    }

    interface OnSearchListener {
        fun onSearch(text: String?)
    }
}