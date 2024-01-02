package com.dr.rajkul.base

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.dr.rajkul.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel

abstract class BaseBottomSheetFragment<V : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> V,
    private val isExpand: Boolean = true,
    private val isFullScreen: Boolean = false,
    private val isDraggable: Boolean = true,
) :
    BottomSheetDialogFragment(), CoroutineScope by CoroutineScope(
    Main
), ViewTreeObserver.OnGlobalLayoutListener {


    val TAG = javaClass.name

    open fun init() {
        setVariables()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    open fun setVariables() {}

    open fun parseArguments() {}

    open fun resume() {}
    open fun pause() {}

    open fun actions() {}

    open fun setUpViews() {
        if (isExpand)
            dialog!!.setOnShowListener { dialog ->
                dialog as BottomSheetDialog
                if (isFullScreen) {
                    setupFullHeight(dialog)
                } else {
                    dialog.behavior.state =
                        BottomSheetBehavior.STATE_EXPANDED
                }
            }
    }


    open fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                ?: return
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.isDraggable = isDraggable
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    open fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    lateinit var mContext: Context

    val binding: V by lazy { bindingFactory(layoutInflater) }

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

    private var ping = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parseArguments()
        if (!ping) {
            ping = true
            init()
            setUpViews()
        }
        actions()
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(this)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    fun snackBar(msg: String, view: View? = dialog?.window?.decorView) {
        view?.let { Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show() }
    }

    fun toast(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

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
        this.cancel()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
        super.onDestroy()
    }

    open fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "")
    }

    public fun goBack() {
        dismiss()
    }


    open fun onKeyboardStateChange(isOpen: Boolean) {}

}
