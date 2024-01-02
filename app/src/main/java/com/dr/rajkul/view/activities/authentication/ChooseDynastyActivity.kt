package com.dr.rajkul.view.activities.authentication

import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import com.dr.rajkul.base.ActivityNavController
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivityChooseDynastyBinding
import com.dr.rajkul.model.ModelDynasty
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding
import com.dr.rajkul.utils.onScrolled
import com.dr.rajkul.view.adapter.rvAdapter.AdapterDynasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChooseDynastyActivity :
    BaseActivity<ActivityChooseDynastyBinding>(ActivityChooseDynastyBinding::inflate),
    OnClickListener {

    private val selected: ModelDynasty? = null


    private val allDynasty = listOf(
        ModelDynasty("Sisodias", "Mewar"),
        ModelDynasty("Kachhwahas", "Jaipur, Alwar, Lawa"),
        ModelDynasty(
            "Rathores",
            "Jodhpur, Bikaner, Kishangarh, Jhabua, Ratlam, Alirajpur, Idar, Seraikela"
        ),
        ModelDynasty("Imperial Pratiharas", "Kannauj"),
        ModelDynasty("Chauhans", "Sambhar, Nadol, Ranthambore, Jalor"),
        ModelDynasty("Tomara dynasty", "Delhi"),
        ModelDynasty("Chalukyas (Solankis)", "Gujarat"),
        ModelDynasty("Vaghelas", "Gujarat"),
        ModelDynasty("Paramaras", "Malwa"),
        ModelDynasty("Paramaras", "Chandravati"),
        ModelDynasty("Gahadavalas", "Varanasi, Kannauj"),
        ModelDynasty("Chandelas", "Jejakabhukti (modern Bundelkhand)"),
        ModelDynasty("Guhilas", "Medapata (modern Mewar)"),
        ModelDynasty("Dogra dynasty", "Jammu & Kashmir"),
        ModelDynasty("Chand dynasty", "Kumaon"),
        ModelDynasty("Katochs", "Kangra"),
        ModelDynasty("Katochs", "Kangra"),
        ModelDynasty("Panwars", "Garhwal"),
        ModelDynasty("Sodhas", "Amarkot"),
        ModelDynasty("Bhatis", "Jaisalmer"),
        ModelDynasty("Bundelas", "Orchha")
    )

    override fun setVariables() {
        super.setVariables()
        binding.listener = this
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.rootView.addSystemWindowInsetToPadding(top = true, bottom = true)

        binding.rvInterest.doOnPreDraw {
            val adapter = AdapterDynasty(
                allDynasty,
                binding.rvInterest.height
            ) { model ->
                binding.txtSelection.text = model.title
                binding.txtSelection.isVisible = true
            }

            binding.rvInterest.adapter = adapter

            binding.rvInterest.smoothScrollToPosition(allDynasty.size / 2)

            launch {
                delay(500)
                binding.rvInterest.onScrolled {
                    binding.txtSwipeExplore.performClick()
                }
            }
        }

    }

    override fun actions() {
        super.actions()

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnBack -> finish()
            binding.txtSwipeExplore -> updateSwipeTextUI()
            binding.btnNext -> ActivityNavController.openSelectProfileActivity(mContext)
        }
    }

    private fun updateSwipeTextUI() {
        if (binding.txtSwipeExplore.isVisible) {
            launch {
                binding.txtSwipeExplore.animate().translationX(300f).duration = 500
                binding.gradientView.animate().translationX(300f).duration = 500
                if (allDynasty.isNotEmpty())
                    binding.rvInterest.smoothScrollToPosition(allDynasty.lastIndex)
                delay(200)
                binding.txtSwipeExplore.isVisible = false
                binding.gradientView.isVisible = false
            }
        }
    }


}