package com.dr.rajkul.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdapter<T, VB : ViewBinding, VH : BaseViewHolder<T, VB>>(
    private val dataList: List<T>
) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = createBinding(inflater, parent, viewType)
        return createViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        // Override this method if your adapter has different view types.
        return super.getItemViewType(position)
    }

    // Implement this method to create and return the ViewBinding instance.
    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VB

    // Implement this method to create and return a specific ViewHolder instance.
    abstract fun createViewHolder(binding: VB, viewType: Int): VH
}

// Update the BaseViewHolder to use ViewBinding
abstract class BaseViewHolder<T, VB : ViewBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}