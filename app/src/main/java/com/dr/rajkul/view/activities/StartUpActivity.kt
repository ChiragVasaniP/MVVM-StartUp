package com.dr.rajkul.view.activities

import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityStartUpBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding

class StartUpActivity : BaseActivity<ActivityStartUpBinding>(ActivityStartUpBinding::inflate) {

    override fun init() {
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.btnRegister.addSystemWindowInsetToMargin(bottom = true)
        binding.imgBackground.addSystemWindowInsetToPadding(top = true)

        binding.btnLogin.setOnClickListener {
            ActivityNavController.openLoginActivity(mContext)
        }

    }

}