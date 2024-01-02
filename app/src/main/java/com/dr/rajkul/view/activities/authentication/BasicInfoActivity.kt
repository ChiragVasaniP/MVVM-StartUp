package com.dr.rajkul.view.activities.authentication

import android.view.View
import android.view.View.OnClickListener
import com.dr.rajkul.R
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.base.NoFilterArrayAdapter
import com.dr.rajkul.databinding.ActivityBasicInfoBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding

class BasicInfoActivity : BaseActivity<ActivityBasicInfoBinding>(ActivityBasicInfoBinding::inflate),
    OnClickListener {

    private var months = emptyList<String>()
    private val years = (1900..2023).map { it.toString() }

    private var malePrefixList = emptyArray<String>()
    private var femalePrefixList = emptyArray<String>()

    private val allPrefix = malePrefixList + femalePrefixList

    private var selectedMonth = ""
    private var selectedYear = "2023"
    private var selectedDay = "01"
    private var selectedGender = ""
    private var selectedPrefix = ""

    private val selectedMonthIndex get() = months.indexOf(selectedMonth) + 1
    private val maxDaysInMonth = getDaysInMonth(selectedMonthIndex, selectedYear.toInt())

    override fun init() {
        super.init()

        months = listOf(
            getString(R.string.january), getString(R.string.february), getString(R.string.march), getString(
                            R.string.april), getString(R.string.may), getString(R.string.june),
            getString(R.string.july), getString(R.string.august), getString(R.string.september), getString(
                            R.string.october), getString(R.string.november), getString(R.string.december)
        )

        selectedMonth = months.first()

        malePrefixList = arrayOf(getString(R.string.kunwar), getString(R.string.bhanwar), getString(
            R.string.tanwar), getString(R.string.thakur_saheb), getString(R.string.banna), getString(
            R.string.data_hukam), getString(R.string.bhabha))

        femalePrefixList = arrayOf(getString(R.string.baisa))

    }

    override fun setUpViews() {
        super.setUpViews()

        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)

        dayAutoComplete()
        monthAutoComplete()
        yearAutoComplete()
        genderAutoComplete()

    }

    private fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnBack -> finish()
            binding.btnNext -> {
                ActivityNavController.openChooseDynastyActivity(mContext)
            }

            binding.edtMonth -> binding.edtMonth.showDropDown()
            binding.edtDay -> binding.edtDay.showDropDown()
            binding.edtYear -> binding.edtYear.showDropDown()
            binding.edtGender -> binding.edtGender.showDropDown()
            binding.edtPrefix -> binding.edtPrefix.showDropDown()

        }
    }

    private fun dayAutoComplete() {

        val days = (1..maxDaysInMonth).map { it.toString() }
        val dateAdapter =
            NoFilterArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, days)
        binding.edtDay.setAdapter(dateAdapter)
        binding.edtDay.setText(days.first().toString())
        binding.edtDay.threshold = 0

    }

    private fun monthAutoComplete() {

        val adapter =
            NoFilterArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, months)
        binding.edtMonth.setAdapter(adapter)
        binding.edtMonth.setAdapter(adapter)
        binding.edtMonth.setText(months.first().toString())
        binding.edtMonth.threshold = 0

        binding.edtMonth.setOnItemClickListener { _, _, i, _ ->
            selectedMonth = months[i]
            val maxDays = getDaysInMonth(selectedMonthIndex, selectedYear.toInt())
            if (binding.edtDay.text.toString().toInt() !in 1..maxDays) {
                dayAutoComplete()
            }
        }

    }

    private fun yearAutoComplete() {

        val adapter =
            NoFilterArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, years)
        binding.edtYear.setAdapter(adapter)
        binding.edtYear.setAdapter(adapter)
        binding.edtYear.setText(selectedYear)
        binding.edtYear.threshold = 0

        binding.edtYear.setOnItemClickListener { _, _, i, _ ->
            selectedYear = years[i]
            val maxDays = getDaysInMonth(selectedMonthIndex, selectedYear.toInt())
            if (binding.edtDay.text.toString().toInt() !in 1..maxDays) {
                dayAutoComplete()
            }
        }

    }

    private fun genderAutoComplete() {

        val genderList = arrayOf(getString(R.string.male), getString(R.string.female))

        val adapter =
            NoFilterArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                genderList.toList()
            )
        binding.edtGender.setAdapter(adapter)
        binding.edtGender.setAdapter(adapter)
        binding.edtGender.threshold = 0

        binding.edtGender.setOnItemClickListener { _, _, i, _ ->
            selectedGender = genderList[i]
            prefixAutoComplete()
        }

    }

    private fun prefixAutoComplete() {

        val adapter =
            NoFilterArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
               (if (selectedGender.isEmpty()) allPrefix else if (selectedGender.equals("Male", true)) malePrefixList else femalePrefixList).toList()
            )

        if (selectedGender == "Male" && selectedGender !in malePrefixList) {
            selectedPrefix = ""
        } else if (selectedGender == "Female" && selectedGender !in femalePrefixList) {
            selectedPrefix = ""
        }

        binding.edtPrefix.setText(selectedPrefix)
        binding.edtPrefix.setAdapter(adapter)
        binding.edtPrefix.setAdapter(adapter)
        binding.edtPrefix.threshold = 0

    }


}