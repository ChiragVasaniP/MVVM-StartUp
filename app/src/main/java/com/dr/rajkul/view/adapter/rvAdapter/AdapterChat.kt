package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dr.rajkul.R
import com.dr.rajkul.databinding.RowItemChatBinding

class AdapterChat(val onClick: () -> Unit): Adapter<AdapterChat.Holder>() {

    inner class Holder(val binding: RowItemChatBinding) : ViewHolder(binding.root), OnClickListener {

        var model: String? = null
        var index = 0

        fun setUp(position: Int) {
            this.index = position
            model = "model"

            binding.listener = this

        }

        override fun onClick(p0: View?) {
             when(p0?.id) {
                 R.id.rootView -> {
                     onClick()
                 }
             }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RowItemChatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setUp(position)
    }

    override fun getItemCount() = 10
}