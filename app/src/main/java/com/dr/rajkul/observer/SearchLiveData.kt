package com.dr.rajkul.observer

import androidx.lifecycle.MutableLiveData

object SearchLiveData : MutableLiveData<HashMap<String, CharSequence>>() {

    fun set(type: String, keyword: CharSequence?) {
        val map = value ?: HashMap()
        map[type] = keyword ?: ""
        postValue(map)
    }

    fun get(type: String): CharSequence {
        val map = value ?: HashMap()
        return map[type] ?: ""
    }
}
