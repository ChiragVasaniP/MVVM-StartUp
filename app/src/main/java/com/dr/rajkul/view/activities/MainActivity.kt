package com.dr.rajkul.view.activities

import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityMainBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private var navHostFragment: NavHostFragment? = null
    lateinit var navController: NavController

    override fun init() {
        super.init()

        navHostFragment =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment?)
                ?: return
        navController = navHostFragment!!.navController

        setBottomNavigation()

    }

    override fun setUpViews() {
        super.setUpViews()
        binding.bottomNavigatioBar.addSystemWindowInsetToMargin(bottom = true)
        binding.appBarLayout.addSystemWindowInsetToPadding(top = true)
    }

    private fun setBottomNavigation() {
        binding.bottomNavigatioBar.setupWithNavController(navController)
    }

    override fun onBackPress() {
        super.onBackPress()
    }

    override fun actions() {
        super.actions()

        navController.addOnDestinationChangedListener { _, dest, _ ->
            binding.bottomNavigatioBar.isVisible = dest.id == R.id.home || dest.id == R.id.chat || dest.id == R.id.notifications || dest.id == R.id.create || dest.id == R.id.marketplace
            binding.appBarLayout.isVisible = dest.id == R.id.home || dest.id == R.id.chat || dest.id == R.id.notifications || dest.id == R.id.create || dest.id == R.id.marketplace
        }

    }

}