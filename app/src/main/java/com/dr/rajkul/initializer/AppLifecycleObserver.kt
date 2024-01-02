package com.dr.rajkul.initializer

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleObserver() : DefaultLifecycleObserver {
    var started = false
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.e(TAG, "onStart")

        if (started) return
        started = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.e(TAG, "onStop")
        started = false
    }

    companion object {
        val TAG = AppLifecycleObserver::class.java.simpleName
    }
}