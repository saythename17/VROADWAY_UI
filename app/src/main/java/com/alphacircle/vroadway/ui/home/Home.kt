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

package com.alphacircle.vroadway.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.PodcastWithExtraInfo
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.ui.home.discover.Discover
import com.alphacircle.vroadway.ui.home.my.My
import com.alphacircle.vroadway.ui.theme.AppTheme
import com.alphacircle.vroadway.ui.theme.MinContrastOfPrimaryVsSurface
import com.alphacircle.vroadway.util.DynamicThemePrimaryColorsFromImage
import com.alphacircle.vroadway.util.LockCategoryIconButton
import com.alphacircle.vroadway.util.NetworkModule
import com.alphacircle.vroadway.util.contrastAgainst
import com.alphacircle.vroadway.util.quantityStringResource
import com.alphacircle.vroadway.util.rememberDominantColorState
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import kotlinx.collections.immutable.PersistentList

@Composable
fun Home(
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: (Long, Int) -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRetry: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    Surface(Modifier.fillMaxSize()) {
        HomeContent(
            featuredPodcasts = viewState.featuredPodcasts,
            isRefreshing = viewState.refreshing,
            homeCategories = viewState.homeCategories,
            selectedHomeCategory = viewState.selectedHomeCategory,
            onCategorySelected = viewModel::onHomeCategorySelected,
            onPodcastUnfollowed = viewModel::onPodcastUnfollowed,
            navigateToPlayer = navigateToPlayer,
            navigateToInfo = navigateToInfo,
            navigateToAccount = navigateToAccount,
            navigateToSettings = navigateToSettings,
            modifier = Modifier.fillMaxSize(),
            onRetry = onRetry
        )
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    featuredPodcasts: PersistentList<PodcastWithExtraInfo>,
    isRefreshing: Boolean,
    selectedHomeCategory: HomeCategory,
    homeCategories: List<HomeCategory>,
    modifier: Modifier = Modifier,
    onPodcastUnfollowed: (String) -> Unit,
    onCategorySelected: (HomeCategory) -> Unit,
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: (Long, Int) -> Unit,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.windowInsetsPadding(
            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
        )
    ) {
        // We dynamically theme this sub-section of the layout to match the selected
        // 'top podcast'

        val surfaceColor = MaterialTheme.colors.surface
        val appBarColor = surfaceColor.copy(alpha = 0.87f)
        val dominantColorState = rememberDominantColorState { color ->
            // We want a color which has sufficient contrast against the surface color
            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
        }

        DynamicThemePrimaryColorsFromImage(dominantColorState) {
            val pagerState = rememberPagerState()

            val selectedImageUrl = featuredPodcasts.getOrNull(pagerState.currentPage)
                ?.podcast?.imageUrl

            // When the selected image url changes, call updateColorsFromImageUrl() or reset()
            LaunchedEffect(selectedImageUrl) {
                if (selectedImageUrl != null) {
                    dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                } else {
                    dominantColorState.reset()
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
//                    .verticalGradientScrim(
//                        color = MaterialTheme.colors.primary.copy(alpha = 0.38f),
//                        startYPercentage = 1f,
//                        endYPercentage = 0f
//                    )
            ) {
                // Draw a scrim over the status bar which matches the app bar
                Spacer(
                    Modifier
                        .background(appBarColor)
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )

                HomeAppBar(
                    backgroundColor = appBarColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    navigateToAccount = navigateToAccount,
                    navigateToSettings = navigateToSettings
                )
            }
        }

        if(!NetworkModule.isOnline(LocalContext.current)) {
            OfflineView(onRetry)
        }

        if (isRefreshing) {
            // TODO show a progress indicator or similar
        }

        if (homeCategories.isNotEmpty()) {
            HomeCategoryTabs(
                categories = homeCategories,
                selectedCategory = selectedHomeCategory,
                onCategorySelected = onCategorySelected
            )
        }

        when (selectedHomeCategory) {
            HomeCategory.My -> {
                My(
                    navigateToPlayer = navigateToPlayer,
                    navigateToInfo = navigateToInfo
                )
            }

            HomeCategory.Discover -> {
                Discover(
                    navigateToPlayer = navigateToPlayer,
                    navigateToInfo = navigateToInfo,
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun HomeAppBar(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    navigateToAccount: () -> Unit,
    navigateToSettings: () -> Unit

) {
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(R.drawable.app_title_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .heightIn(max = 32.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconButton(
                    onClick = { navigateToAccount() }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(R.string.cd_account)
                    )
                }
                IconButton(
                    onClick = { navigateToSettings() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.cd_settings)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun HomeCategoryTabs(
    categories: List<HomeCategory>,
    selectedCategory: HomeCategory,
    onCategorySelected: (HomeCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeCategoryTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) },
                text = {
                    Text(
                        text = when (category) {
                            HomeCategory.My -> stringResource(R.string.home_my)
                            HomeCategory.Discover -> stringResource(R.string.home_discover)
                        },
                        style = MaterialTheme.typography.body2
                    )
                }
            )
        }
    }
}

@Composable
fun HomeCategoryTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FollowedPodcasts(
    items: PersistentList<PodcastWithExtraInfo>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onPodcastUnfollowed: (String) -> Unit,
) {
    HorizontalPager(
        pageCount = items.size,
        state = pagerState,
        modifier = modifier
    ) { page ->
        val (podcast, lastEpisodeDate) = items[page]
        FollowedPodcastCarouselItem(
            podcastImageUrl = podcast.imageUrl,
            podcastTitle = podcast.title,
            onUnfollowedClick = { onPodcastUnfollowed(podcast.uri) },
            lastEpisodeDateText = lastEpisodeDate?.let { lastUpdated(it) },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun FollowedPodcastCarouselItem(
    modifier: Modifier = Modifier,
    podcastImageUrl: String? = null,
    podcastTitle: String? = null,
    lastEpisodeDateText: String? = null,
    onUnfollowedClick: () -> Unit,
) {
    Column(
        modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .aspectRatio(1f)
        ) {
            if (podcastImageUrl != null) {
                AsyncImage(
                    model = podcastImageUrl,
                    contentDescription = podcastTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                )
            }

            LockCategoryIconButton(
                onClick = onUnfollowedClick,
                isLock = true, /* All podcasts are followed in this feed */
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        if (lastEpisodeDateText != null) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = lastEpisodeDateText,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun lastUpdated(updated: OffsetDateTime): String {
    val duration = Duration.between(updated.toLocalDateTime(), LocalDateTime.now())
    val days = duration.toDays().toInt()

    return when {
        days > 28 -> stringResource(R.string.updated_longer)
        days >= 7 -> {
            val weeks = days / 7
            quantityStringResource(R.plurals.updated_weeks_ago, weeks, weeks)
        }

        days > 0 -> quantityStringResource(R.plurals.updated_days_ago, days, days)
        else -> stringResource(R.string.updated_today)
    }
}

@Preview
@Composable
fun PreviewHomeAppBar() {
    AppTheme {
        HomeAppBar(backgroundColor = Color.Transparent, navigateToSettings = {}, navigateToAccount = {})
    }
}

@Preview
@Composable
fun PreviewPodcastCard() {
    AppTheme {
        FollowedPodcastCarouselItem(
            modifier = Modifier.size(128.dp),
            onUnfollowedClick = {}
        )
    }
}
