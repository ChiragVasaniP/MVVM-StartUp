package com.dr.rajkul.observer

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

object NetworkManager : MediatorLiveData<Boolean>(), CoroutineScope {
    private val TAG = NetworkManager::class.java.simpleName
    private const val clientUrl = "http://clients3.google.com/generate_204"

    override val coroutineContext = Dispatchers.Main
    private var networkCallback = NetworkCallback()
    private lateinit var connectivityManager: ConnectivityManager

    fun init(context: Context) {
        connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun postValue(value: Boolean?) {
        if (this.value == value) return
        super.postValue(value)
    }

    override fun onActive() {
        super.onActive()
        addNetworkRequest()
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun addNetworkRequest() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        checkCapabilities()
    }


    private fun checkCapabilities() {
        val hasCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        postValue(hasCapabilities == true)
    }

    private var connecting = false
    private suspend fun checkInternet() = withContext(Dispatchers.IO) {
        if (connecting) return@withContext
        connecting = true

        var hasNetwork = false
        try {
            val url = URL(clientUrl).openConnection() as HttpURLConnection
            url.setRequestProperty("User-Agent", "Android")
            url.setRequestProperty("Accept-Encoding", "identity")
            url.setRequestProperty("Connection", "close")
            url.connectTimeout = 1500
            url.connect()
            hasNetwork = url.responseCode == 204 && url.contentLength == 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            hasNetwork = false
        } finally {
            connecting = false
            postValue(hasNetwork)
            if (!hasNetwork) checkConnection(2500)
        }
    }


    private fun checkConnection(delay: Long = 0) {
        launch {
            delay(delay)
            checkInternet()
        }
    }

    private class NetworkCallback : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.e(TAG, "Active connection")
            checkConnection()

        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.e(TAG, "Lost active connection")
            checkConnection()
        }
    }
}





