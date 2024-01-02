package com.dr.rajkul.view.fragment.chat

import android.view.View
import android.view.View.OnClickListener
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseFragment
import com.dr.rajkul.databinding.FragmentAllChatBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.view.adapter.rvAdapter.AdapterChat

class AllChatFragment : BaseFragment<FragmentAllChatBinding>(FragmentAllChatBinding::inflate), OnClickListener {


    override fun init() {
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.rvChats.addSystemWindowInsetToMargin(bottom = true)

        binding.rvChats.adapter = AdapterChat() {
            ActivityNavController.openChatViewActivity(mContext)
        }

    }

    override fun onClick(p0: View?) {

    }

}