package com.rezza.articletestapps.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.entities.News

class NewsAdapter(sources: ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.AdapterView?>() {
    private val listData: ArrayList<News>
    private var listener: OnSelectedListener? = null
    private var context: Context? = null

    init {
        listData = sources
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterView {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.news_adapter, parent, false)
        context = parent.context
        return AdapterView(itemView)
    }

    override fun onBindViewHolder(holder: AdapterView, position: Int) {
        val news: News = listData[position]
        if (news.isLoading) {
            holder.mrly_body.visibility = View.GONE
            holder.shmr_load.visibility = View.VISIBLE
            holder.shmr_load.startShimmerAnimation()
        } else {
            holder.mrly_body.visibility = View.VISIBLE
            holder.shmr_load.visibility = View.GONE
            holder.shmr_load.stopShimmerAnimation()
            holder.txvw_title.text = news.title
            val release: String = news.category + " | " + news.publishedAt
            holder.txvw_release.text = release
            Glide.with(context!!).load(news.urlToImage).into(holder.imvw_image)
            holder.mrly_body.setOnClickListener {
                if (listener != null) {
                    listener!!.onSelected(news)
                }
            }
        }
    }


    class AdapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txvw_title: TextView
        var txvw_release: TextView
        var imvw_image: ImageView
        var mrly_body: MaterialRippleLayout
        var shmr_load: ShimmerFrameLayout

        init {
            txvw_release = itemView.findViewById(R.id.txvw_release)
            txvw_title = itemView.findViewById(R.id.txvw_title)
            mrly_body = itemView.findViewById(R.id.mrly_body)
            imvw_image = itemView.findViewById(R.id.imvw_image)
            shmr_load = itemView.findViewById(R.id.shmr_load)
        }
    }

    fun setOnSelectedListener(listener: OnSelectedListener?) {
        this.listener = listener
    }

    interface OnSelectedListener {
        fun onSelected(data: News?)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}