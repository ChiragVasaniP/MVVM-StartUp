package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemStoryBinding
import com.dr.rajkul.model.DemoItem

class StoryAdapter(private val demoItemList: ArrayList<DemoItem>) :
    BaseAdapter<DemoItem, RowItemStoryBinding, StoryAdapter.ItemViewHolder>(demoItemList) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = RowItemStoryBinding.inflate(inflater, parent, false)

    override fun createViewHolder(binding: RowItemStoryBinding, viewType: Int) = ItemViewHolder(binding)
    class ItemViewHolder(private val itemBinding: RowItemStoryBinding) :
        BaseViewHolder<DemoItem, RowItemStoryBinding>(itemBinding) {

        override fun bind(demoItem: DemoItem) {
            // Perform binding logic here to bind data to the views using the ViewBinding instance.

            itemBinding.storyText.text = demoItem.name
            itemBinding.storyProfilePhoto.setImageResource(R.drawable.profile)
        }
    }


}
