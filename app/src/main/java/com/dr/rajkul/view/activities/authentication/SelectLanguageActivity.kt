package com.dr.rajkul.view.activities.authentication

import android.content.res.Configuration
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.core.view.isVisible
import com.dr.rajkul.R
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivitySelectLanguageBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding
import com.dr.rajkul.utils.justTry
import java.util.Locale

class SelectLanguageActivity : BaseActivity<ActivitySelectLanguageBinding>(ActivitySelectLanguageBinding::inflate), OnClickListener {

    override fun init() {
        super.init()
        changeLanguage("hi")
        languageSelection(0)
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)
        binding.txtChooseLanguage.text = getString(R.string.choose_nyour_language)
        binding.btnContinue.text = getString(R.string.str_continue)
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun onClick(p0: View?) {
         when(p0) {
             binding.btnContinue -> {
                 ActivityNavController.openStartUpActivity(this)
             }
             binding.cardHindi -> {
                 languageSelection(0)
                 changeLanguage("hi")
             }
             binding.cardSanskrit -> {
                 languageSelection(1)
                 changeLanguage("sa")
             }
             binding.cardRajasthani -> {
                 languageSelection(2)
                 changeLanguage("raj")
             }
             binding.cardEnglish -> {
                 languageSelection(3)
                 changeLanguage("en")
             }
         }
    }

    private fun languageSelection(position: Int) {
        val buttons = arrayOf(binding.cardHindi, binding.cardSanskrit, binding.cardRajasthani, binding.cardEnglish)
        buttons.forEachIndexed() { index, button ->
            button.strokeWidth = if (index == position) 12 else 4
            justTry {
                (button.getChildAt(1) as ImageView).isVisible = index == position
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        val newLocale = Locale(languageCode)
        val configuration = resources.configuration
        configuration.setLocale(newLocale)
        mContext.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        setUpViews()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


}