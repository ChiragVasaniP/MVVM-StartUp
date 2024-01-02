package com.dr.rajkul.view.fragment.myprofile

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dr.rajkul.base.BaseFragment
import com.dr.rajkul.databinding.FragmentPostsBinding
import com.dr.rajkul.view.adapter.rvAdapter.ProfilePostAdapter
import com.dr.rajkul.model.DemoItem

class PostsFragment : BaseFragment<FragmentPostsBinding>(FragmentPostsBinding::inflate) {

    override fun setUpViews() {
        super.setUpViews()
        setupAdapters()
    }

    private fun setupAdapters() {
        binding.rvPostsProfile.adapter = ProfilePostAdapter(arrayListOf(
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),
            DemoItem(""),))
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvPostsProfile.layoutManager = layoutManager
    }
}