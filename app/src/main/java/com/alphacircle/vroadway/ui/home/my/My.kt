package com.alphacircle.vroadway.ui.home.my

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.Category
import com.alphacircle.vroadway.ui.components.NoListView
import com.alphacircle.vroadway.ui.home.category.EpisodeList
import com.alphacircle.vroadway.ui.home.category.PodcastCategoryViewModel
import com.alphacircle.vroadway.ui.home.discover.DiscoverViewModel
import com.alphacircle.vroadway.ui.theme.AppTheme
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.util.viewModelProviderFactoryOf

@Composable
fun My(
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        TitleBar(title = stringResource(id = R.string.my_download_title))

        val viewModel: DiscoverViewModel = viewModel()
        val viewState by viewModel.state.collectAsStateWithLifecycle()

        val selectedCategory = viewState.selectedCategory

        when(selectedCategory) {
//            is Category ->
//                Crossfade(
//                    targetState = selectedCategory,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                ) { category ->
//                    /**
//                     * TODO, need to think about how this will scroll within the outer VerticalScroller
//                     */
//                    val viewModel: PodcastCategoryViewModel = viewModel(
//                        // We use a custom key, using the category parameter
//                        key = "category_list_${category!!.id}",
//                        factory = viewModelProviderFactoryOf { PodcastCategoryViewModel(category!!.id) }
//                    )
//
//                    val viewState by viewModel.state.collectAsStateWithLifecycle()
//                    EpisodeList(viewState.episodes, navigateToPlayer, navigateToInfo)
//
//                }
//
//            null -> NoListView()

            is Category -> NoListView(text = stringResource(id = R.string.no_download_list))
        }


    }

}


@Composable
fun TitleBar(title: String) {
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    )
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics { heading() },
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    )
}

@Preview
@Composable
fun PreviewTitleBar() {
    TitleBar(stringResource(id = R.string.my_download_title))
}

@Preview
@Composable
fun PreviewMy() {
    My(navigateToPlayer = {}, navigateToInfo = {}, Modifier)
}