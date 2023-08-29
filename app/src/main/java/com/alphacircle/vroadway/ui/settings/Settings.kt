package com.alphacircle.vroadway.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Dock
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Dvr
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.MenuIcon
import com.alphacircle.vroadway.ui.components.MenuItem
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.theme.VroadwayColors

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
    /**
     * TODO: save this value in Internal Storage and get from Internal Storage
     */
    var downloadOnly by remember { mutableStateOf(true) }
    val context = LocalContext.current
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Wifi),
        name = R.string.setting_item_download,
        onClick = {
            when {
                downloadOnly -> {
                    downloadOnly = false
                    Toast.makeText(context, "Downloads on Wi-Fi Only Off", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    downloadOnly = true
                    Toast.makeText(context, "Downloads on Wi-Fi Only On", Toast.LENGTH_SHORT).show()
                }
            }
        },
        color = if(downloadOnly) VroadwayColors.primary else VroadwayColors.onSurface
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.QrCode),
        name = R.string.setting_item_hmd,
        onClick = {}
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.MenuBook),
        name = R.string.setting_item_guide,
        onClick = navigateToGuides
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Notifications),
        name = R.string.setting_item_notices,
        onClick = navigateToNotices
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Policy),
        name = R.string.setting_item_policy,
        onClick = navigateToLicense
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingsPreview() {
    Settings({}, {}, {}, {})
}