package com.dr.rajkul.observer

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

class ActivityLifeCycle : ActivityLifecycleCallbacks {
    var current: Activity? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        current = activity
    }

    override fun onActivityStarted(activity: Activity) {
        current = activity
    }

    override fun onActivityResumed(activity: Activity) {
        current = activity
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        // don't clear current activity because activity may get stopped after the new activity is resumed
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        // don't clear current activity because activity may get destroyed after the new activity is resumed
    }
}