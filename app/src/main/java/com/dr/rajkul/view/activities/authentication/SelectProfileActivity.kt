package com.dr.rajkul.view.activities.authentication

import android.content.res.ColorStateList
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dr.rajkul.R
import com.dr.rajkul.base.BaseActivity
import com.dr.rajkul.databinding.ActivitySelectProfileBinding
import com.dr.rajkul.utils.Inset.addSystemWindowInsetToPadding
import com.dr.rajkul.utils.mCheckPermissions
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar

class SelectProfileActivity : BaseActivity<ActivitySelectProfileBinding>(ActivitySelectProfileBinding::inflate), OnClickListener {

    private var imgUri: Uri? = null

    override fun init() {
        super.init()

        val directory = File(filesDir, "all")
        if(!directory.exists()){
            directory.mkdirs()
        }

        launch {
            if (mCheckPermissions(android.Manifest.permission.CAMERA)) {
                val file = File(directory,"${Calendar.getInstance().timeInMillis}.png")
                imgUri = FileProvider.getUriForFile(this@SelectProfileActivity, applicationContext.packageName + ".provider", file)
            }

        }
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
        when(p0) {
            binding.btnUploadPhoto -> {
                launch {
                    if (mCheckPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        getImageFromGalleryLauncher.launch("image/*")
                    }
                }
            }
            binding.btnTakePhoto -> {
                launch {
                    if (mCheckPermissions(android.Manifest.permission.CAMERA)) {
                        if (imgUri != null) {
                            getImageFromCameraLauncher.launch(imgUri)
                        } else {
                            Log.d(TAG, "onClick: Camera uri not found")
                        }
                    }
                }
            }
        }
    }

    private val getImageFromGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imgUri = uri
        if (imgUri != null) {
            binding.profileLayout.imgProfile.setImageURI(imgUri)
            binding.profileLayout.frameProfile.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, R.color.colorLight))
            // cropImageRequest(imgUri!!)
        }
    }

    private val getImageFromCameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            if (imgUri != null) {
              //  cropImageRequest(imgUri!!)
                binding.profileLayout.imgProfile.setImageURI(imgUri)
                binding.profileLayout.frameProfile.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.colorLight))
            }
        }
    }

}