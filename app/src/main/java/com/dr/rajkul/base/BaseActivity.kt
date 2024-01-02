package com.dr.rajkul.base

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.viewbinding.ViewBinding
import com.dr.rajkul.databinding.LayoutCustomSnackbarBinding
import com.dr.rajkul.dialog.Loading
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseActivity<V : ViewBinding>(val bindingFactory: (LayoutInflater) -> V) :
    AppCompatActivity(), CoroutineScope by CoroutineScope(
    Dispatchers.Main
), ViewTreeObserver.OnGlobalLayoutListener {


    lateinit var loading: Loading

    val TAG = javaClass.name

    var statusBarColor: Int? = null

    open fun init() {
        setVariables()
    }

    open fun setVariables() {}

    open fun actions() {}

    open fun setUpViews() {}

    open fun observeData() {
    }

    open fun onShowProgress() {
        loading.showLoading()
    }

    open fun onHideProgress() {
       loading.dismissLoading()
    }

    lateinit var mContext: Context

    val binding: V by lazy { bindingFactory(layoutInflater) }

    open fun setPreWindowConfig() { }

    open fun onBackPress() {
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPreWindowConfig()
       // window.statusBarColor = statusBarColor ?: getColor(R.color.white)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mContext = this
        setContentView(binding.root)
        loading = Loading(mContext).build()
        init()
        setUpViews()
        observeData()
        actions()

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                 onBackPress()
            }
        })

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(this)

    }

    fun snackBar(msg: String) {

        try {
            val snackBar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)

            snackBar.view.updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = Gravity.TOP
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)) {
                    updateMargins(top = 150, left = 10, right = 10)
                }
                ViewCompat.setLayoutDirection(snackBar.view, ViewCompat.LAYOUT_DIRECTION_LOCALE)
            }

            val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
            snackBarLayout.setPadding(0, 0, 0, 0)

            val binding = LayoutCustomSnackbarBinding.inflate(LayoutInflater.from(this))
            binding.text.text = msg
            snackBarLayout.addView(binding.root)

            snackBar.show()
        } catch (e: Exception) {
            toast(msg)
        }

    }

    fun snackBar(view: View, msg: String) {
        try {
            val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)

            snackBar.view.updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = Gravity.TOP
                if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)) {
                    updateMargins(top = 150, left = 10, right = 10)
                }
                ViewCompat.setLayoutDirection(snackBar.view, ViewCompat.LAYOUT_DIRECTION_LOCALE)
            }

            val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
            snackBarLayout.setPadding(0, 0, 0, 0)

            val binding = LayoutCustomSnackbarBinding.inflate(LayoutInflater.from(this))
            binding.text.text = msg
            snackBarLayout.addView(binding.root)

            snackBar.show()
        } catch (e: Exception) {
            toast(msg)
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        this.cancel()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
        super.onDestroy()
    }

    public fun goBack() {
        finish()
    }

    fun showAlert(title: String, msg: String? = null, @DrawableRes icon: Int? = null, cancellable: Boolean = true, buttonName: String, buttonClick: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(title).also {
                if (msg != null)
                    it.setMessage(msg)
                if (icon != null)
                    it.setIcon(icon)
            }
            .setCancelable(cancellable)
            .setNeutralButton(buttonName){_,_->
                buttonClick()
            }.create().show()
    }

    override fun onGlobalLayout() {
        val rect = Rect()
        binding.root.getWindowVisibleDisplayFrame(rect)
        val screenHeight: Int = binding.root.rootView.height
        val keyboardHeight: Int = screenHeight - rect.bottom
        if (keyboardHeight > screenHeight * 0.15) {
            // Keyboard is showing
            onKeyboardStateChange(true)
        } else {
            onKeyboardStateChange(false)
            // Keyboard is hiding
        }
    }

    fun showProgress()  {
        if (this::loading.isInitialized) loading.showLoading()
    }

    fun dismissProgress()  {
        if (this::loading.isInitialized) loading.dismissLoading()
    }


    open fun onKeyboardStateChange(isOpen: Boolean) {}

}