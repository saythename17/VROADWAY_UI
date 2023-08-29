/*
 * Copyright 2020 The Android Open Source Project
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

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.account.Account
import com.alphacircle.vroadway.ui.account.PurchasedHistory
import com.alphacircle.vroadway.ui.home.Home
import com.alphacircle.vroadway.ui.info.Info
import com.alphacircle.vroadway.ui.player.PlayerScreen
import com.alphacircle.vroadway.ui.player.PlayerViewModel
import com.alphacircle.vroadway.ui.settings.Guides
import com.alphacircle.vroadway.ui.settings.Policy
import com.alphacircle.vroadway.ui.settings.Notices
import com.alphacircle.vroadway.ui.settings.Settings
import com.alphacircle.vroadway.ui.splash.Splash

@Composable
fun VroadwayApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    appState: VroadwayAppState = rememberVroadwayAppState()
) {
    val infoArgCategoryId = "categoryId"
    val infoArgContentIndex = "index"
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) { _ ->
                Splash(navigateToHome = { appState.navigateToHome() })
            }
            composable(Screen.Home.route) { backStackEntry ->
                Home(
                    navigateToPlayer = { episodeUri ->
                        appState.navigateToPlayer(episodeUri, backStackEntry)
                    },
                    navigateToInfo = { categoryId, index -> appState.navigateToInfo(categoryId, index) },
                    navigateToAccount = { appState.navigateToAccount() },
                    navigateToSettings = { appState.navigateToSettings() },
                    onRetry = { appState.refreshOnline() }
                )
            }
            composable(
                Screen.Info.route,
                arguments = listOf(navArgument(infoArgCategoryId) { defaultValue = 6L },
                    navArgument(infoArgContentIndex) { defaultValue = 0 }
                )
            ) { backStageEntry ->
                Info(
                    onBackPress = appState::navigateBack,
                    backStageEntry.arguments!!.getLong(infoArgCategoryId),
                    backStageEntry.arguments!!.getInt(infoArgContentIndex)
                )
            }
            composable(Screen.Account.route) { _ ->
                Account(
                    onBackPress = appState::navigateBack,
                    navigateToPurchasedHistory = appState::navigateToPurchasedHistory
                )
            }
            composable(Screen.PurchasedHistory.route) {
                PurchasedHistory(onBackPress = appState::navigateBack)
            }
            composable(Screen.Settings.route) { _ ->
                Settings(
                    onBackPress = appState::navigateBack,
                    navigateToGuides = appState::navigateToGuides,
                    navigateToNotices = appState::navigateToNotices,
                    navigateToLicense = appState::navigateToLicense
                )
            }
            composable(Screen.Guides.route) { _ ->
                Guides(onBackPress = appState::navigateBack)
            }
            composable(Screen.License.route) { _ ->
                Policy(onBackPress = appState::navigateBack)
            }
            composable(Screen.Notices.route) { _ ->
                Notices(onBackPress = appState::navigateBack)
            }
            composable(Screen.Player.route) { backStackEntry ->
                val playerViewModel: PlayerViewModel = viewModel(
                    factory = PlayerViewModel.provideFactory(
                        owner = backStackEntry,
                        defaultArgs = backStackEntry.arguments
                    )
                )
                PlayerScreen(
                    playerViewModel,
                    windowSizeClass,
                    displayFeatures,
                    onBackPress = appState::navigateBack
                )
            }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.connection_error_title)) },
        text = { Text(text = stringResource(R.string.connection_error_message)) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(stringResource(R.string.retry_label))
            }
        }
    )
}

@Preview
@Composable
fun DialogPreview() {
    OfflineDialog {}
}
