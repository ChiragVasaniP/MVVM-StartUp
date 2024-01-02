package com.dr.rajkul.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object KeyboardUtil {
    fun hideKeyboard(view: View?) {
        if (view == null) return
        val inputMethodManager =
            view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                ?: return
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity?) {
        val view = activity?.currentFocus ?: return
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                ?: return
        //        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(
            activity.window.decorView.windowToken,
            0
        )
    }

    fun showKeyboard(view: View?) {
        if (view == null) return
        val imm =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                ?: return
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    private val imeInset = WindowInsetsCompat.Type.ime()

    fun View.isVisible() = ViewCompat.getRootWindowInsets(this)?.isVisible(imeInset) == true

}