package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemMentionsBinding
import com.dr.rajkul.model.DemoItem

class MentionAdapter(private val demoItemList: ArrayList<DemoItem>) :
    BaseAdapter<DemoItem, RowItemMentionsBinding, MentionAdapter.ItemViewHolder>(demoItemList) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = RowItemMentionsBinding.inflate(inflater, parent, false)

    override fun createViewHolder(binding: RowItemMentionsBinding, viewType: Int) = ItemViewHolder(binding)

    class ItemViewHolder(private val itemBinding: RowItemMentionsBinding) :
        BaseViewHolder<DemoItem, RowItemMentionsBinding>(itemBinding) {

        override fun bind(demoItem: DemoItem) {
            // Perform binding logic here to bind data to the views using the ViewBinding instance.
            itemBinding.userName.text = demoItem.name

        }
    }

}