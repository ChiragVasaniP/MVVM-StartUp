package com.dr.rajkul.view.activities.authentication

import android.annotation.SuppressLint
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityRegisterBinding
import com.dr.rajkul.dialog.choosecountry.DialogChooseCountry
import com.dr.rajkul.model.CountryData
import com.dr.rajkul.utils.AppHelper
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToMargin
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate), OnClickListener {

    private lateinit var viewModel: AuthenticationViewModel

    private var selectedCountry: CountryData? = AppHelper.getCountries().find { it.name?.equals("India", true) == true }

    override fun init() {
        viewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()

        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)
        binding.btnLogin.addSystemWindowInsetToMargin(bottom = true)

        setCountyName()

    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    @SuppressLint("SetTextI18n")
    private fun setCountyName() {
        binding.txtCountryCode.text =  selectedCountry?.flag + "  +" +  selectedCountry?.dial_code
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.btnContinue -> {
                ActivityNavController.openPhoneVerificationActivity(mContext)
            }
            binding.btnLogin -> {
                finish()
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
    }

}