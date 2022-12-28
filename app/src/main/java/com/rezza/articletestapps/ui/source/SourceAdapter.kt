package com.rezza.articletestapps.ui.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balysv.materialripple.MaterialRippleLayout
import com.rezza.articletestapps.R
import com.rezza.articletestapps.entities.Source
import java.util.ArrayList

class SourceAdapter internal constructor(sources: ArrayList<Source>) :
    RecyclerView.Adapter<SourceAdapter.AdapterView?>() {
    private val listData: ArrayList<Source>
    private var listener: OnSelectedListener? = null

    init {
        listData = sources
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterView {
        val itemView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.source_adapter, parent, false)
        return AdapterView(itemView)
    }

    override fun onBindViewHolder(holder: AdapterView, position: Int) {
        val source: Source = listData[position]
        holder.txvw_title.text = source.name
        holder.txvw_description.text = source.description
        holder.mrly_action.setOnClickListener {
            if (listener != null) {
                listener!!.onSelected(source)
            }
        }
    }


    override fun getItemCount(): Int {
       return listData.size
    }

    class AdapterView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txvw_title: TextView
        var txvw_description: TextView
        var mrly_action: MaterialRippleLayout

        init {
            txvw_description = itemView.findViewById(R.id.txvw_description)
            txvw_title = itemView.findViewById(R.id.txvw_title)
            mrly_action = itemView.findViewById(R.id.mrly_action)
        }
    }

    fun setOnSelectedListener(listener: OnSelectedListener?) {
        this.listener = listener
    }

    interface OnSelectedListener {
        fun onSelected(data: Source?)
    }


}