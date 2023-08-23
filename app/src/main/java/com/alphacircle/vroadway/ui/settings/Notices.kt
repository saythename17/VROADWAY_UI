package com.alphacircle.vroadway.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.AccordionListSample
import com.alphacircle.vroadway.ui.components.AccordionList
import com.alphacircle.vroadway.ui.components.NoListView
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.info.ContentInfoViewModel
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.util.viewModelProviderFactoryOf

@Composable
fun Notices(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    val viewModel: BoardViewModel = viewModel(factory = viewModelProviderFactoryOf { BoardViewModel("notice") })
    val viewState by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.setting_item_notices))
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            AccordionList(list = viewState.boardList, boardType = "notice")
        }
    }
}


@Preview
@Composable
fun PreviewNotices() {
    Guides {}
}