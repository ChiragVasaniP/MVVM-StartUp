package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemProfilePostsBinding
import com.dr.rajkul.model.DemoItem

class ProfilePostAdapter(private val demoItemList: ArrayList<DemoItem>) :
    BaseAdapter<DemoItem, RowItemProfilePostsBinding, ProfilePostAdapter.ItemViewHolder>(demoItemList) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = RowItemProfilePostsBinding.inflate(inflater, parent, false)

    override fun createViewHolder(binding: RowItemProfilePostsBinding, viewType: Int) = ItemViewHolder(binding)

    class ItemViewHolder(private val itemBinding: RowItemProfilePostsBinding) :
        BaseViewHolder<DemoItem, RowItemProfilePostsBinding>(itemBinding) {

        override fun bind(demoItem: DemoItem) {
            // Perform binding logic here to bind data to the views using the ViewBinding instance.
            itemBinding.photo.setImageResource(R.drawable.post)
        }
    }


}