package com.dr.rajkul.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import com.dr.rajkul.utils.KeyboardUtil.isVisible


object Inset {
    fun View.addSystemWindowInsetToPadding(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = initialLeft + (if (left) systemBar.left else 0),
                top = initialTop + (if (top) systemBar.top else 0),
                right = initialRight + (if (right) systemBar.right else 0),
                bottom = initialBottom + (if (bottom) systemBar.bottom else 0)
            )
            insets
        }
    }

    fun View.addSystemWindowInsetToMargin(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(marginLeft, marginTop, marginRight, marginBottom)

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams {
                (this as? ViewGroup.MarginLayoutParams)?.let {
                    updateMargins(
                        left = initialLeft + (if (left) systemBar.left else 0),
                        top = initialTop + (if (top) systemBar.top else 0),
                        right = initialRight + (if (right) systemBar.right else 0),
                        bottom = initialBottom + (if (bottom) systemBar.bottom else 0)
                    )
                }
            }
            insets
        }
    }

    fun View.addSystemWindowInsetToPaddingWithKeyboardAdjustment(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            if (!isVisible()) {
                view.updatePadding(
                    left = initialLeft + (if (left) systemBar.left else 0),
                    top = initialTop + (if (top) systemBar.top else 0),
                    right = initialRight + (if (right) systemBar.right else 0),
                    bottom = initialBottom + (if (bottom) systemBar.bottom else 0)
                )
            }
            insets
        }
        addKeyboardHeightAdjustmentPadding(top = top, bottom = bottom)
    }

    fun View.addKeyboardHeightAdjustmentPadding(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(paddingLeft, paddingTop, paddingRight, paddingBottom)

        val callback = object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
                val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                updatePadding(
                    left = initialLeft + (if (left) systemBar.left else 0),
                    top = initialTop + (if (top) systemBar.top else 0),
                    right = initialRight + (if (right) systemBar.right else 0),
                    bottom = initialBottom +
                            when {
                                bottom -> if (ime.bottom > systemBar.bottom && isVisible()) ime.bottom else systemBar.bottom
                                ime.bottom > systemBar.bottom -> ime.bottom
                                else -> 0
                            }
                )
                return insets
            }
        }
        ViewCompat.setWindowInsetsAnimationCallback(this, callback)
    }


    fun View.addSystemWindowInsetToMarginWithKeyboardAdjustment(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(marginLeft, marginTop, marginRight, marginBottom)

        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.applyMargin(
                start = initialLeft + (if (left) systemBar.left else 0),
                top = initialTop + (if (top) systemBar.top else 0),
                end = initialRight + (if (right) systemBar.right else 0),
                bottom = initialBottom + (if (bottom) systemBar.bottom else 0)
            )
            insets
        }
        addKeyboardHeightAdjustmentMargin(top = top, bottom = bottom)
    }


    private fun View.addKeyboardHeightAdjustmentMargin(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
    ) {
        val (initialLeft, initialTop, initialRight, initialBottom) =
            listOf(marginLeft, marginTop, marginRight, marginBottom)

        val callback = object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
                val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                applyMargin(
                    start = initialLeft + (if (left) systemBar.left else 0),
                    top = initialTop + (if (top) systemBar.top else 0),
                    end = initialRight + (if (right) systemBar.right else 0),
                    bottom = initialBottom + if (bottom) if (ime.bottom > systemBar.bottom) ime.bottom else systemBar.bottom
                    else if (ime.bottom > systemBar.bottom) ime.bottom else 0
                )
                return insets
            }
        }
        ViewCompat.setWindowInsetsAnimationCallback(this, callback)
    }

    internal fun View.applyMargin(
        start: Int? = null,
        top: Int? = null,
        end: Int? = null,
        bottom: Int? = null,
    ) {
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
                marginStart = start ?: marginStart
                topMargin = top ?: topMargin
                marginEnd = end ?: marginEnd
                bottomMargin = bottom ?: bottomMargin
            }
        }
    }
}