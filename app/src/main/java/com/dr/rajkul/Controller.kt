package com.dr.rajkul

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.dr.rajkul.initializer.AppLifecycleObserver
import com.dr.rajkul.observer.ActivityLifeCycle
import com.dr.rajkul.utils.PreferenceManager

class Controller: Application() {

    companion object {
        private val TAG = Controller::class.java.name
        lateinit var globalContext: Context
    }

    private var activityLifeCycle = ActivityLifeCycle()
    fun getCurrentActivity(): Activity? = activityLifeCycle.current

    override fun onCreate() {
        super.onCreate()
        globalContext = this
        PreferenceManager.init(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
        registerActivityLifecycleCallbacks(activityLifeCycle)
    }

}