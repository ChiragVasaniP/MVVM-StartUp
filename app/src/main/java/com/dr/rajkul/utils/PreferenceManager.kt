package com.dr.rajkul.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("com.rajkul", Context.MODE_PRIVATE)
    }
    
    fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getString(key: String, defaultString: String?): String? {
        return sharedPreferences.getString(key, defaultString)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun removeKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, default: Float = 0f): Float {
        return sharedPreferences.getFloat(key, default)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, default: Int = 0): Int {
        return sharedPreferences.getInt(key, default)
    }

    fun clearPreference() {
        sharedPreferences.edit().clear().apply()
    }
}