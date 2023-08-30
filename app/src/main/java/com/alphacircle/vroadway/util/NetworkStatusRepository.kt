package com.alphacircle.vroadway.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.RemoteException
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * State hierarchy for different Network Status connections
 */
sealed class NetworkStatusState {
    /* Device has a valid internet connection */
    object NetworkStatusConnected : NetworkStatusState()

    /* Device has no internet connection */
    object NetworkStatusDisconnected : NetworkStatusState()
}

@RequiresApi(Build.VERSION_CODES.M)
class NetworkStatusRepository constructor(
    private val context: Context,
    private val mainDispatcher: CoroutineDispatcher,
    private val appScope: CoroutineScope
) {

    private val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var callback: ConnectivityManager.NetworkCallback? = null
    private var receiver: ConnectivityReceiver? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private val _state = MutableStateFlow(getCurrentNetwork())
    @RequiresApi(Build.VERSION_CODES.M)
    val state: StateFlow<NetworkStatusState> = _state

    init {
        _state
            .subscriptionCount
            .map { count -> count > 0 } // map count into active/inactive flag
            .distinctUntilChanged() // only react to true<->false changes
            .onEach { isActive ->
                /** Only subscribe to network callbacks if we have an active subscriber */
                if (isActive) subscribe()
                else unsubscribe()
            }
            .launchIn(appScope)
    }

    /* Simple getter for fetching network connection status synchronously */
    @RequiresApi(Build.VERSION_CODES.M)
    fun hasNetworkConnection() = getCurrentNetwork() == NetworkStatusState.NetworkStatusConnected

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCurrentNetwork(): NetworkStatusState {
        return try {
            cm.getNetworkCapabilities(cm.activeNetwork)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .let { connected ->
                    if (connected == true) NetworkStatusState.NetworkStatusConnected
                    else NetworkStatusState.NetworkStatusDisconnected
                }
        } catch (e: RemoteException) {
            NetworkStatusState.NetworkStatusDisconnected
        }
    }

    private fun subscribe() {
        // just in case
        if (callback != null || receiver != null) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            callback = NetworkCallbackImpl().also { cm.registerDefaultNetworkCallback(it) }
        } else {
            receiver = ConnectivityReceiver().also {
                context.registerReceiver(it, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        }

        /* emit our initial state */
        emitNetworkState(getCurrentNetwork())
    }

    private fun unsubscribe() {

        if (callback == null && receiver == null) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            callback?.run { cm.unregisterNetworkCallback(this) }
            callback = null
        } else {
            receiver?.run { context.unregisterReceiver(this) }
            receiver = null
        }
    }

    private fun emitNetworkState(newState: NetworkStatusState) {
        appScope.launch(mainDispatcher) {
            _state.emit(newState)
        }
    }

    private inner class ConnectivityReceiver : BroadcastReceiver() {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {

            /** emit the new network state */
            intent
                .getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
                ?.isConnectedOrConnecting
                .let { connected ->
                    if (connected == true) emitNetworkState(NetworkStatusState.NetworkStatusConnected)
                    else emitNetworkState(getCurrentNetwork())
                }
        }
    }

    private inner class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) = emitNetworkState(NetworkStatusState.NetworkStatusConnected)

        override fun onLost(network: Network) = emitNetworkState(NetworkStatusState.NetworkStatusDisconnected)
    }
}