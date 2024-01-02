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

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import com.alphacircle.vroadway.di.DependencyInjectionHelper
import com.alphacircle.vroadway.ui.home.NetworkStatusViewModel
import com.alphacircle.vroadway.ui.theme.AppTheme
import com.alphacircle.vroadway.util.NetworkStatusRepository
import com.alphacircle.vroadway.util.NetworkStatusState
import com.google.accompanist.adaptive.calculateDisplayFeatures
class MainActivity : ComponentActivity() {

    /** The main repo handling network callbacks */
    private val repo: NetworkStatusRepository by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DependencyInjectionHelper.injectRepo(applicationContext)
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }

    /** ViewModel for consuming network changes */
    private val networkViewModel: NetworkStatusViewModel by viewModels {
        DependencyInjectionHelper.injectViewModelFactory(repo)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 저장 폴더에 대한 읽기/쓰기 권한을 확인합니다.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        }

        // This app draws behind the system bars, so we want to handle fitting system windows
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)

            AppTheme {
                VroadwayApp(
                    windowSizeClass,
                    displayFeatures,
                    networkViewModel
                )
            }
        }
    }

    // 권한 요청 결과를 처리합니다.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되었습니다.
        } else {
            // 권한이 거부되었습니다.
            // 적절한 조치를 취합니다.
        }
    }
}
