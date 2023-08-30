package com.alphacircle.vroadway.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.alphacircle.vroadway.ui.home.NetworkStatusViewModel
import com.alphacircle.vroadway.util.NetworkStatusRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * @author Omid
 *
 * Dependency Injection Black Box
 */
object DependencyInjectionHelper {

    /**
     * Note: [context] should be the [Context.getApplicationContext]
     * [appScope] should be created in your subclass of [android.app.Application]
     * or via your DI framework and passed in. (There should only be one instance)
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun injectRepo(
        context: Context,
        mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
        appScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    ): NetworkStatusRepository {
        return NetworkStatusRepository(context, mainDispatcher, appScope)
    }

    fun injectViewModelFactory(repository: NetworkStatusRepository): NetworkStatusViewModel.Factory {
        return NetworkStatusViewModel.Factory(repository)
    }
}