package com.dr.rajkul.initializer

import android.content.Context
import androidx.startup.Initializer
import com.dr.rajkul.observer.NetworkManager
import com.dr.rajkul.utils.PreferenceManager

class AppInitializer : Initializer<Boolean> {

    override fun create(context: Context): Boolean {
        PreferenceManager.init(context)
        NetworkManager.init(context)
        return true
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}