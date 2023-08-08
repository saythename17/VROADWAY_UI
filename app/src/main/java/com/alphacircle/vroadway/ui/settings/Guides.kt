package com.alphacircle.vroadway.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.AccordionListSample
import com.alphacircle.vroadway.ui.components.AccordionList
import com.alphacircle.vroadway.ui.components.TopBar

@Composable
fun Guides(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.setting_item_guide))
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            AccordionList(list = AccordionListSample)
        }
    }
}


@Preview
@Composable
fun PreviewGuides() {
    Guides {}
}