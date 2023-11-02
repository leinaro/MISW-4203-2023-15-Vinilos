package com.misw.vinilos.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

sealed class NetworkStatus {
    object Unknown: NetworkStatus()
    object Connected: NetworkStatus()
    object Disconnected: NetworkStatus()
}

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}

class NetworkConnectivityServiceImpl @Inject constructor(
    @ApplicationContext val context: Context
): NetworkConnectivityService {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun networkConnection(): Boolean {
        var networkAvailable = false
        val network: Network? = connectivityManager.activeNetwork
        val networkCapabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(network)

        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
            networkAvailable = true
        } else if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
            networkAvailable = true
        } else if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true) {
            networkAvailable = true
        }

        return networkAvailable
    }

    override val networkStatus: Flow<NetworkStatus> = callbackFlow {
        val connectivityCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.Connected)
            }

            override fun onUnavailable() {
                trySend(NetworkStatus.Disconnected)
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.Disconnected)
            }

        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, connectivityCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
}
