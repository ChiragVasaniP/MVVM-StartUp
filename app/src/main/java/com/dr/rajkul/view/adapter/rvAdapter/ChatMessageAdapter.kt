package com.dr.rajkul.view.adapter.rvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dr.rajkul.base.BaseAdapter
import com.dr.rajkul.base.BaseViewHolder
import com.dr.rajkul.databinding.RowItemChatMessageBinding
import com.dr.rajkul.model.ChatMessagePOJO

class ChatMessageAdapter(private val chatList : List<ChatMessagePOJO>):BaseAdapter<ChatMessagePOJO,RowItemChatMessageBinding,ChatMessageAdapter.ChatMessageViewHolder>(chatList) {




    class ChatMessageViewHolder(private val itemBinding:RowItemChatMessageBinding):BaseViewHolder<ChatMessagePOJO,RowItemChatMessageBinding>(itemBinding){
        override fun bind(item: ChatMessagePOJO) {
            itemBinding.senderTextView.text = item.sender
            itemBinding.messageTextView.text =item.receiver
        }

    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) =  RowItemChatMessageBinding.inflate(inflater,parent,false)

    override fun createViewHolder(
        binding: RowItemChatMessageBinding,
        viewType: Int
    )= ChatMessageViewHolder(binding)
}