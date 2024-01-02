package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemPostBinding
import com.dr.rajkul.view.adapter.vpAdapter.PostPagerAdapter
import com.dr.rajkul.model.DemoItem

class PostAdapter(private val demoItemList: ArrayList<DemoItem>) :
    BaseAdapter<DemoItem, RowItemPostBinding, PostAdapter.ItemViewHolder>(demoItemList) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = RowItemPostBinding.inflate(inflater, parent, false)

    override fun createViewHolder(binding: RowItemPostBinding, viewType: Int) = ItemViewHolder(binding)
    class ItemViewHolder(private val itemBinding: RowItemPostBinding) :
        BaseViewHolder<DemoItem, RowItemPostBinding>(itemBinding) {

        override fun bind(demoItem: DemoItem) {
            // Perform binding logic here to bind data to the views using the ViewBinding instance.
            itemBinding.vpPostPhoto.adapter = PostPagerAdapter(arrayListOf(
                DemoItem("sample"),
                DemoItem("test")
            ))
            itemBinding.userName.text = demoItem.name
            itemBinding.storyProfilePhoto.setImageResource(R.drawable.profile)
        }
    }

}