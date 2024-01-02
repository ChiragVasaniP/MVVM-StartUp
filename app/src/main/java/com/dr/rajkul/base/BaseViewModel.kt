package com.dr.rajkul.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job

open class BaseViewModel: ViewModel(), CoroutineScope by CoroutineScope(Main) {

    var showProgress = MutableLiveData<Boolean?>()
    private var _customLoadingMsg = MutableLiveData<String?>()
    var toast = MutableLiveData<String?>()
    val customMessage: String? get() = _customLoadingMsg.value

    lateinit var lifecycleOwner: LifecycleOwner

    open fun init() { }

    fun toast(msg: String) {
        toast.postValue(msg)
    }

    fun showProgress() {
        _customLoadingMsg.postValue(null)
        showProgress.postValue(true)
    }

    fun showProgress(msg: String) {
        _customLoadingMsg.postValue(msg)
        showProgress.postValue(true)
    }

    fun hideProgress() {
        _customLoadingMsg.postValue(null)
        showProgress.postValue(false)
    }


    var apiProgress: MutableLiveData<Boolean> = MutableLiveData()
    var apiError: MutableLiveData<String?> = MutableLiveData()
    var apiSuccess: MutableLiveData<String?> = MutableLiveData()

    var jobs = HashMap<String, Job>()


    fun onError(message: String?) {
        apiError.postValue(message)
    }

    fun onResponse(message: String?) {
        apiSuccess.postValue(message)
    }

    fun addNewJob(name: String, job: Job) {
        this.jobs[name] = job
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.value.cancel() }
    }

}