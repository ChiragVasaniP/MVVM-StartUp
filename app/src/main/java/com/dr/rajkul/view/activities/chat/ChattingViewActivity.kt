package com.dr.rajkul.view.activities.chat

import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityChatingViewBinding
import com.dr.rajkul.model.ChatMessagePOJO
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.view.adapter.rvAdapter.ChatMessageAdapter

class ChattingViewActivity : BaseActivity<ActivityChatingViewBinding>(ActivityChatingViewBinding::inflate) {

    override fun init() {
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rootView.addSystemWindowInsetToMargin(bottom = true, top = true)
        binding.rvChats.adapter = ChatMessageAdapter(listOf(ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),ChatMessagePOJO(),))
    }

    override fun actions() {
        super.actions()

    }

}