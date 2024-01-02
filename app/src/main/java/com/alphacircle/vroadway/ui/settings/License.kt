package com.alphacircle.vroadway.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.AccordionListSample
import com.alphacircle.vroadway.ui.components.AccordionList
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.util.viewModelProviderFactoryOf

@Composable
fun License(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

//    val viewModel: BoardViewModel = viewModel(factory = viewModelProviderFactoryOf { BoardViewModel("license") })
//    val viewState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.setting_item_license))
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            LicenseItem(licenseTitle = "cardboard", licenseUrl = "https://github.com/googlevr/cardboard")
            LicenseItem(licenseTitle = "", licenseUrl = "")
//            AccordionList(list = viewState.boardList, boardType = "policy")

        }
    }
}

@Composable
fun LicenseItem(
    licenseTitle: String,
    licenseUrl: String
) {
    Column(
        modifier = Modifier.padding(24.dp, 8.dp)
    ) {
        Text(text = licenseTitle, style = EnglishTypography.body2)
        Text(text = licenseUrl, style = EnglishTypography.caption, color = VroadwayColors.primaryVariant)
    }
}


@Preview
@Composable
fun PreviewLicenses() {
    License {}
}