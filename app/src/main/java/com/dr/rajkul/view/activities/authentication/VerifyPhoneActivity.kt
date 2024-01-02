package com.dr.rajkul.view.activities.authentication

import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.dr.rajkul.R
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityVerifyPhoneBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding
import com.dr.rajkul.utils.hideKeyboard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VerifyPhoneActivity :
    BaseActivity<ActivityVerifyPhoneBinding>(ActivityVerifyPhoneBinding::inflate), OnClickListener {

    var otp: String = ""

    override fun init() {
        super.init()
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.btnBack -> {
                finish()
            }

            binding.txtVerify -> {
                hideKeyboard()
                if (validate()) {
                    launch {
                        onShowProgress()
                        delay(1000)
                        onHideProgress()
                        ActivityNavController.openChooseUsernameActivity(mContext)
                    }
                }
            }

        }
    }

    private fun validate() = if (otp.length >= 6) true else if (otp.isEmpty()) {
        snackBar(getString(R.string.enter_otp))
        false
    } else {
        snackBar(getString(R.string.enter_valid_otp))
        false
    }

    override fun actions() {
        super.actions()
        binding.edtVerificationCode.addTextChangedListener {
            otp = it.toString()
        }
    }

    override fun onShowProgress() {
        binding.txtVerify.isVisible = false
        binding.pb.isVisible = true
    }

    override fun onHideProgress() {
        binding.txtVerify.isVisible = true
        binding.pb.isVisible = false
    }

}