package com.dr.rajkul.observer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

fun <T> LiveData<T>.debounce(
    duration: Long = 300L,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) = MediatorLiveData<T>().also { mld ->

    val source = this
    var job: Job? = null

    mld.addSource(source) {
        if (mld.value == null) {
            mld.postValue(it)
            return@addSource
        }
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mld.postValue(source.value)
        }
    }
}



