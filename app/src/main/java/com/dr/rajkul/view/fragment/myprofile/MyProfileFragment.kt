package com.dr.rajkul.view.fragment.myprofile

import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseFragment
import com.dr.rajkul.databinding.CustomTabLayoutBinding
import com.dr.rajkul.databinding.FragmentMyProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {


    override fun setUpViews() {
        super.setUpViews()

        setupTabLayout()

    }

    private fun setupTabLayout() {

    }


    private fun createTabView(tabLayout: TabLayout, position: Int): View {
        val tabView = CustomTabLayoutBinding.inflate(layoutInflater,tabLayout,false)

        val tabTextView = tabView.tabTextView
        tabTextView.text = when (position) {
            0 -> "Posts"
            1 -> "Mentions"
            else -> "Unknown"
        }
        return tabView.root
    }

    private fun updateTabView(tab: TabLayout.Tab, isSelected: Boolean) {
        val tabView = tab.customView
        if (tabView != null) {
            val tabLinearLayout = tabView.findViewById<LinearLayout>(R.id.ll_tabView)
            val tvTabLayout = tabView.findViewById<TextView>(R.id.tabTextView)

            val backgroundDrawable = if (isSelected) {
                tvTabLayout.setTextColor(ContextCompat.getColor(mContext,R.color.colorDark))
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.round_corners_8
                )
            }else {
                tvTabLayout.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                ContextCompat.getDrawable(mContext, R.drawable.rounded_corners_post)

            }
            tabLinearLayout.background = backgroundDrawable
        }
    }
}