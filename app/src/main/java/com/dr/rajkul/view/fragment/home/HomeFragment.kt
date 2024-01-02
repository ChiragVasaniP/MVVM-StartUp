package com.dr.rajkul.view.fragment.home

import com.dr.rajkul.base.BaseFragment
import com.dr.rajkul.databinding.FragmentHomeBinding
import com.dr.rajkul.model.DemoItem
import com.dr.rajkul.view.adapter.rvAdapter.PostAdapter
import com.dr.rajkul.view.adapter.rvAdapter.StoryAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun init() {
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()
        setAdapter()
    }

    private fun setAdapter() {
        val list = ArrayList<DemoItem>()
        list.add(DemoItem("You"))
        list.add(DemoItem("Kuldeep"))
        list.add(DemoItem("Sunil"))
        list.add(DemoItem("Chirag"))
        list.add(DemoItem("Lalit"))
        binding.rvStory.adapter = StoryAdapter(list)
        binding.rvPosts.adapter = PostAdapter(list)

    }

}

