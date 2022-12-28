package com.rezza.articletestapps.ui.source

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.data.SourceResponseModel
import com.rezza.articletestapps.entities.Category
import com.rezza.articletestapps.entities.Source
import com.rezza.articletestapps.ui.base.MyActivity
import com.rezza.articletestapps.ui.article.NewsActivity
import com.rezza.articletestapps.ui.view.ErrorDialog
import com.rezza.articletestapps.ui.view.SearchView
import java.util.*

class SourceActivity : MyActivity() {
    private var scvw_search: SearchView? = null
    private var txvw_header: TextView? = null
    private var shmr_load: ShimmerFrameLayout? = null
    private val listSource: ArrayList<Source> = ArrayList<Source>()
    private val listSourceFilter: ArrayList<Source> = ArrayList<Source>()
    private var mCategory: Category? = null
    private var mAdapter: SourceAdapter? = null
    private var sourceViewModel: SourceViewModel? = null
    override fun setLayout(): Int {
        return R.layout.source_activity
    }

    override fun initLayout() {

        scvw_search = findViewById(R.id.scvw_search)
        scvw_search?.createWithTyping(getResources().getString(R.string.search_source))
        txvw_header = findViewById(R.id.txvw_header)
        shmr_load = findViewById(R.id.shmr_load)
        val rcvw_source: RecyclerView = findViewById(R.id.rcvw_source)
        rcvw_source.setLayoutManager(LinearLayoutManager(mActivity))
        mAdapter = SourceAdapter(listSourceFilter)
        rcvw_source.setAdapter(mAdapter)
        shmr_load?.startShimmerAnimation()
        init()
    }

    override fun initListener() {
        scvw_search?.setonSearchListener (object  : SearchView.OnSearchListener{
            override fun onSearch(text: String?) {
                filter(text!!)
            }
        })

        mAdapter!!.setOnSelectedListener( object  : SourceAdapter.OnSelectedListener {
            override fun onSelected(data: Source?) {
                val intent = Intent(mActivity, NewsActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }

        })

        findViewById<MaterialRippleLayout>(R.id.mrly_back).setOnClickListener { onBackPressed() }
    }

    private fun init() {
        listSource.clear()
        mCategory = intent.getSerializableExtra("category") as Category
        txvw_header?.text = mCategory!!.name
        sourceViewModel = ViewModelProvider(this).get(SourceViewModel::class.java)
        loadData()
    }

    private fun loadData() {
        sourceViewModel!!.getSource(mCategory!!.name)
        sourceViewModel!!.getLiveData().observe(
            this,
            Observer { sourceResponseModel: SourceResponseModel? ->
                if (sourceResponseModel == null) {
                    hideLoading()
                    return@Observer
                }
                if (sourceResponseModel.code != "ok") {
                    showError(sourceResponseModel.message)
                    hideLoading()
                    return@Observer
                }
                for (data in sourceResponseModel.sources) {
                    val source = Source()
                    source.category = data.category
                    source.name = data.name
                    source.id = data.id
                    source.description = data.description
                    listSource.add(source)
                }
                filter("")
                hideLoading()
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filter(text: String) {
        listSourceFilter.clear()
        if (text.isEmpty()) {
            listSourceFilter.addAll(listSource)
        } else {
            for (source in listSource) {
                if (source.name?.lowercase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                    listSourceFilter.add(source)
                }
            }
        }
        mAdapter!!.notifyDataSetChanged()
    }

    private fun hideLoading() {
        if (shmr_load!!.visibility == View.VISIBLE) {
            shmr_load!!.visibility = View.GONE
            shmr_load!!.stopShimmerAnimation()
        }
    }

    private fun showError(message: String) {
        val dialog = ErrorDialog(mActivity)
        dialog.show("Failed API", message)
    }
}