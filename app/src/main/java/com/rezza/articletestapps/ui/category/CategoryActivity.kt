package com.rezza.articletestapps.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rezza.articletestapps.R
import com.rezza.articletestapps.entities.Category
import com.rezza.articletestapps.ui.base.MyActivity
import com.rezza.articletestapps.ui.view.SearchView
import java.util.*

class CategoryActivity : MyActivity(), CategoryAdapter.OnSelectedListener{

    private var scvw_search: SearchView? = null
    private var listCategory: ArrayList<Category> = ArrayList<Category>()
    private var mAdapter: CategoryAdapter? = null


    override fun setLayout(): Int {
        return R.layout.category_activity
    }

    override fun initLayout() {
        scvw_search = findViewById(R.id.scvw_search)
        scvw_search!!.create(getResources().getString(R.string.search_news))

        val rcvw_category: RecyclerView = findViewById(R.id.rcvw_category)
        rcvw_category.layoutManager = GridLayoutManager(this, 3)
        rcvw_category.isNestedScrollingEnabled = false

        mAdapter = CategoryAdapter(listCategory)
        rcvw_category.adapter = mAdapter
        loadData()


    }

    override fun initListener() {
        scvw_search!!.setOnActionListener(object :SearchView.OnActionListener {
            override fun onAction() {
                val intent = Intent(mActivity, FindAllActivity::class.java)
                startActivity(intent)
            }
        })

        mAdapter!!.setOnSelectedListener(object : CategoryAdapter.OnSelectedListener {
            override fun onSelected(data: Category?) {
//                val intent = Intent(mActivity, SourceActivity::class.java)
//                intent.putExtra("category", data)
//                startActivity(intent)
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        for (category in Category.values()){
            listCategory.add(category)
        }
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onSelected(data: Category?) {
        TODO("Not yet implemented")
    }
}