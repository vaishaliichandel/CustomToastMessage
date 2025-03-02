package com.vaishali.customblinkbutton.helper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vaishali.customblinkbutton.R

class AdapterRecyclerView constructor(
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ListHolder(
            inflater.inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListHolder) {
            holder.txtTitle.text = list[position]
            holder.parent.setOnClickListener {
                callback.onItemClick(list[position])
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    private class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent: RelativeLayout
        val txtTitle: TextView

        init {
            parent = itemView.findViewById(R.id.item_list_parent)
            txtTitle = itemView.findViewById(R.id.item_list_txtTitle)
        }
    }

    interface Callback {
        fun onItemClick(message: String?)
    }

}