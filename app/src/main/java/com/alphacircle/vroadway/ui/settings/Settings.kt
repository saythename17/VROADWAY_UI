package com.alphacircle.vroadway.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Dvr
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.MenuIcon
import com.alphacircle.vroadway.ui.components.MenuItem
import com.alphacircle.vroadway.ui.components.TopBar

@Composable
fun Settings(
    onBackPress: () -> Unit,
    navigateToGuides: () -> Unit,
    navigateToNotices: () -> Unit,
    navigateToLicense: () -> Unit,
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.cd_settings))
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            SettingItemList(
                navigateToGuides = navigateToGuides,
                navigateToNotices = navigateToNotices,
                navigateToLicense = navigateToLicense
            )
        }
    }
}

@Composable
fun SettingItemList(
    navigateToGuides: () -> Unit,
    navigateToNotices: () -> Unit,
    navigateToLicense: () -> Unit,
) {
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Wifi),
        name = R.string.setting_item_download,
        onClick = {}
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.QrCode),
        name = R.string.setting_item_hmd,
        onClick = {}
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Dvr),
        name = R.string.setting_item_guide,
        onClick = navigateToGuides
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Notifications),
        name = R.string.setting_item_notices,
        onClick = navigateToNotices
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.DocumentScanner),
        name = R.string.setting_item_policy,
        onClick = navigateToLicense
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings({}, {}, {}, {})
}