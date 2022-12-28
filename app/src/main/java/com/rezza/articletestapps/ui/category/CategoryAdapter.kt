package com.rezza.articletestapps.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.entities.Category

class CategoryAdapter internal constructor(private val listData: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.AdapterView?>() {
    private var listener: OnSelectedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterView {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_adapter, parent, false)
        return AdapterView(itemView)
    }

    override fun onBindViewHolder(holder: AdapterView, position: Int) {
        val category = listData[position]
        holder.im_icon.setImageResource(category.pIcon)
        holder.tv_title.text = category.pName

        holder.mrly_action.setOnClickListener {
            if (listener != null) {
                listener!!.onSelected(category)
            }
        }
    }


    class AdapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var im_icon: ImageView
        var tv_title: TextView
        var mrly_action: MaterialRippleLayout

        init {
            im_icon = itemView.findViewById(R.id.imvw_icon)
            tv_title = itemView.findViewById(R.id.txvw_title)
            mrly_action = itemView.findViewById(R.id.mrly_action)
        }
    }

    fun setOnSelectedListener(listener: OnSelectedListener?) {
        this.listener = listener
    }

    interface OnSelectedListener {
        fun onSelected(data: Category?)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}