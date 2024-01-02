package com.dr.rajkul.view.adapter.vpAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemVpPostBinding
import com.dr.rajkul.model.DemoItem

class PostPagerAdapter(private val demoItemList: ArrayList<DemoItem>) :
    BaseAdapter<DemoItem, RowItemVpPostBinding, PostPagerAdapter.ItemViewHolder>(demoItemList) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = RowItemVpPostBinding.inflate(inflater, parent, false)

    override fun createViewHolder(binding: RowItemVpPostBinding, viewType: Int) = ItemViewHolder(binding)
    class ItemViewHolder(private val itemBinding: RowItemVpPostBinding) :
        BaseViewHolder<DemoItem, RowItemVpPostBinding>(itemBinding) {

        override fun bind(demoItem: DemoItem) {
            // Perform binding logic here to bind data to the views using the ViewBinding instance.
            itemBinding.photo.setImageResource(R.drawable.post)
            
        }
    }


}






