package com.dr.rajkul.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dr.rajkul.R
import com.dr.rajkul.dialog.Loading
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


abstract class BaseFragment<V : ViewBinding>(val bindingFactory: (LayoutInflater) -> V) :
    Fragment(), CoroutineScope by CoroutineScope(
    Dispatchers.Main
), ViewTreeObserver.OnGlobalLayoutListener {

    open var useSharedViewModel: Boolean = false

    lateinit var loading: Loading

    val TAG = javaClass.name

    open fun init() {
        setVariables()
    }

    open fun setVariables() {}

    open fun parseArguments() {}

    open fun resume() {}
    open fun pause() {}

    open fun optionMenu() {}

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


    fun confirmDialog(
        message: String,
        positiveButton: String? = null,
        negativeButton: String? = null,
        title: String? = null,
        onYes: () -> Unit
    ) {
        AlertDialog.Builder(mContext)
            .setTitle(title ?: "")
            .setMessage(message)
            .setPositiveButton(positiveButton ?: getString(R.string.yes)) { _, _ ->
                onYes()
            }.setNegativeButton(negativeButton ?: getString(R.string.no), null).create().show()
    }

    lateinit var mContext: Context

    val binding: V by lazy { bindingFactory(layoutInflater) }

    fun showAlert(
        title: String,
        msg: String? = null,
        @DrawableRes icon: Int? = null,
        setCancelable: Boolean = true,
        buttonName: String,
        buttonClick: () -> Unit
    ) {
        AlertDialog.Builder(mContext)
            .setTitle(title).also {
                if (msg != null)
                    it.setMessage(msg)
                if (icon != null)
                    it.setIcon(icon)
            }
            .setCancelable(setCancelable)
            .setNeutralButton(buttonName) { _, _ ->
                buttonClick()
            }.create().show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onResume() {
        resume()
        super.onResume()
    }

    override fun onPause() {
        pause()
        super.onPause()
    }

    private lateinit var ping: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parseArguments()
        if (::ping.isInitialized.not()) {
            init()
            setUpViews()
            loading = Loading(mContext).build()
        }
        observeData()
        actions()
        ping = ""
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        optionMenu()
    }

    fun snackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
        super.onDestroy()
    }

    open fun onKeyboardStateChange(isOpen: Boolean) {}


}