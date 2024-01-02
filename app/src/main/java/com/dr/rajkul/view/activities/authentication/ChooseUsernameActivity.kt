package com.dr.rajkul.view.activities.authentication

import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityChooseUsernameBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ChooseUsernameActivity :
    BaseActivity<ActivityChooseUsernameBinding>(ActivityChooseUsernameBinding::inflate),
    OnClickListener {

    private val isUsernameAvailable = MutableLiveData<Boolean?>(null)

    override fun init() {
        super.init()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)

    }

    override fun actions() {
        super.actions()
        binding.edtUserName.addTextChangedListener {
            launch {
                delay(500)
                isUsernameAvailable.value = Random.nextInt(2) == 1
            }
        }
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun observeData() {
        super.observeData()

        isUsernameAvailable.observe(this) {
            if (it == null) return@observe
            binding.pb.isVisible = false
            binding.taken.isVisible = !it
            binding.icVerified.isVisible = it
        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnBack -> finish()
            binding.btnNext -> {
                ActivityNavController.openBasicInfoActivity(mContext)
            }
        }
    }


}