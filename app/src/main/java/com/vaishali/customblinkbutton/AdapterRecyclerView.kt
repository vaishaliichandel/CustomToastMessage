package com.vaishali.customblinkbutton

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vaishali.customblinkbutton.AdapterRecyclerView.ListHolder
import com.vaishali.customblinkbutton.databinding.ItemListBinding

class AdapterRecyclerView(
    private val callback: Callback
) : RecyclerView.Adapter<ListHolder>() {

    private var list: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = ItemListBinding.inflate(inflater, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.binding.tvTitle.text = list[position]
        holder.binding.root.setOnClickListener {
            callback.onItemClick(list[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ListHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onItemClick(message: String?)
    }

}