package com.dr.rajkul.view.fragment.myprofile

import com.dr.rajkul.base.BaseFragment
import com.dr.rajkul.databinding.FragmentMentionsBinding
import com.dr.rajkul.view.adapter.rvAdapter.MentionAdapter
import com.dr.rajkul.model.DemoItem

class MentionsFragment : BaseFragment<FragmentMentionsBinding>(FragmentMentionsBinding::inflate) {

    override fun init() {
        super.init()

    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rvMentions.adapter = MentionAdapter(arrayListOf(
            DemoItem("lalit"),
            DemoItem("sunil"),
            DemoItem("chirag"),
            DemoItem("puneet"),
            DemoItem("shubham"),
            DemoItem("kuldeep"),))
    }

}