package com.alphacircle.vroadway.ui.home

import android.os.Build
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alphacircle.vroadway.util.NetworkStatusRepository
import com.alphacircle.vroadway.util.NetworkStatusState
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModelProvider

/**
 * @author Omid
 *
 * A [ViewModel] that provides a [networkState] where consumers can observe changes
 * in the network state and react, such as showing or hiding a offline bar in the UI.
 *
 * See the [NetworkStatusRepository] for how the network state is obtained and managed
 */
class NetworkStatusViewModel(
    private val repo: NetworkStatusRepository
) : ViewModel() {

    /** [StateFlow] emitting a [NetworkStatusState] every time it changes */
    @RequiresApi(Build.VERSION_CODES.M)
    val networkState: StateFlow<NetworkStatusState> = repo.state


    /* Simple getter for fetching network connection status synchronously */
    @RequiresApi(Build.VERSION_CODES.M)
    fun isDeviceOnline() : Boolean = repo.hasNetworkConnection()

    class Factory constructor(
        private val networkRepository: NetworkStatusRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NetworkStatusViewModel(networkRepository) as T
        }
    }
}