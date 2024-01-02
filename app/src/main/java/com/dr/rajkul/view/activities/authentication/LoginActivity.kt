package com.dr.rajkul.view.activities.authentication

import android.annotation.SuppressLint
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityLoginBinding
import com.dr.rajkul.dialog.choosecountry.DialogChooseCountry
import com.dr.rajkul.model.CountryData
import com.dr.rajkul.utils.AppHelper
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), OnClickListener {

    private lateinit var viewModel: AuthenticationViewModel

    private var selectedCountry: CountryData? = AppHelper.getCountries().find { it.name?.equals("India", true) == true }

    override fun init() {
        viewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)
        binding.btnRegister.addSystemWindowInsetToMargin(bottom = true)

        setCountyName()

    }

    @SuppressLint("SetTextI18n")
    private fun setCountyName() {
        binding.txtCountryCode.text =  selectedCountry?.flag + "  +" +  selectedCountry?.dial_code
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.btnRegister -> {
                ActivityNavController.openRegisterActivity(mContext)
            }
            binding.btnLogin, binding.btnLoginOnKeyboardOpen -> {
                ActivityNavController.openMainActivity(mContext)
            }
            binding.llCountryCode -> {
                DialogChooseCountry(selectedCountry) {
                    selectedCountry = it
                    setCountyName()
                }.show(supportFragmentManager)
            }
        }
    }

    override fun onKeyboardStateChange(isOpen: Boolean) {
        super.onKeyboardStateChange(isOpen)
        binding.btnLoginOnKeyboardOpen.isVisible = isOpen
    }

}