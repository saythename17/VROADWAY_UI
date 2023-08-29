/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alphacircle.vroadway.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * List of screens for [VroadwayApp]
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Info : Screen("info/{categoryId}/{index}") {
        fun createRoute(categoryId: Long, index: Int) = "info/$categoryId/$index"
    }
    object Account : Screen("account")
    object PurchasedHistory : Screen("purchased")
    object Settings : Screen("settings")
    object Guides : Screen("guides")
    object Notices : Screen("notices")
    object License : Screen("license")
    object Player : Screen("player/{episodeUri}") {
        fun createRoute(episodeUri: String) = "player/$episodeUri"
    }
}

@Composable
fun rememberVroadwayAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    VroadwayAppState(navController, context)
}

class VroadwayAppState(
    val navController: NavHostController,
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }

    fun navigateToHome() {
        navController.navigate(Screen.Home.route) {
            popUpTo(0) // NOTE: Delete previous Splash Screen from navigation Stack
        }
    }

    fun navigateToPlayer(episodeUri: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            val encodedUri = Uri.encode(episodeUri)
            navController.navigate(Screen.Player.createRoute(encodedUri))
        }
    }

    fun navigateToAccount() {
        navController.navigate(Screen.Account.route)
    }

    fun navigateToPurchasedHistory() {
        navController.navigate(Screen.PurchasedHistory.route)
    }

    fun navigateToSettings() {
        navController.navigate(Screen.Settings.route)
    }

    fun navigateToGuides() {
        navController.navigate(Screen.Guides.route)
    }

    fun navigateToNotices() {
        navController.navigate(Screen.Notices.route)
    }

    fun navigateToLicense() {
        navController.navigate(Screen.License.route)
    }

    fun navigateToInfo(categoryId: Long, index: Int) {
        navController.navigate(Screen.Info.createRoute(categoryId, index))
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            cm?.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
