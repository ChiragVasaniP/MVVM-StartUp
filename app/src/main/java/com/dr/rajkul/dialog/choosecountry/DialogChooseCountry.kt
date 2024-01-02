package com.dr.rajkul.dialog.choosecountry

import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import com.dr.rajkul.base.BaseBottomSheetFragment
import com.dr.rajkul.databinding.DialogChooseCountryBinding
import com.dr.rajkul.model.CountryData
import com.dr.rajkul.utils.AppHelper
import com.dr.rajkul.utils.KeyboardUtil
import java.util.ArrayList

class DialogChooseCountry(
    alreadySelected: CountryData? = null,
    private val onSelect: (country: CountryData) -> Unit
) : BaseBottomSheetFragment<DialogChooseCountryBinding>(
    isFullScreen = true,
    isDraggable = false,
    bindingFactory = DialogChooseCountryBinding::inflate
),
    OnClickListener {

    private var selectedCountry: CountryData? =
        alreadySelected ?: AppHelper.getCountries().find { it.name?.equals("India", true) == true }

    override fun init() {
        super.init()

        binding.listener = this

    }

    override fun setUpViews() {
        super.setUpViews()

        binding.rootView.updatePadding(
            bottom = 150
        )

        setUpRv()

    }

    private fun setUpRv() {
        binding.rvCountries.adapter = AdapterCountries(AppHelper.getCountries().onEach {
            it.selected = it.name == selectedCountry?.name
        }) {
            selectedCountry = it
        }
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun actions() {
        super.actions()
        binding.edtSearch.addTextChangedListener {
            if (it.toString().isEmpty().not()) {
                search(it.toString())
                return@addTextChangedListener
            }
            binding.txtNoResult.isVisible = false
            setUpRv()
        }
    }

    private fun search(s: String) {
        val searchList = ArrayList<CountryData>()
            .apply {
                addAll(
                    AppHelper.getCountries().filter {
                        it.name?.contains(s, true) == true || it.dial_code?.contains(
                            s,
                            true
                        ) == true
                    })
            }

        binding.txtNoResult.isVisible = searchList.isEmpty()

        binding.rvCountries.adapter = AdapterCountries(searchList.onEach {
            it.selected = it.name == selectedCountry?.name
        }) {
            selectedCountry = it
        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnNext, binding.btnNextOnKeyBoardOpen -> {
                dismiss()
                KeyboardUtil.hideKeyboard(binding.edtSearch)
                selectedCountry?.let { onSelect.invoke(it) }
            }

            binding.btnBack -> dismiss()
        }
    }

    override fun onKeyboardStateChange(isOpen: Boolean) {
        super.onKeyboardStateChange(isOpen)
        binding.btnNextOnKeyBoardOpen.isVisible = isOpen
    }

}