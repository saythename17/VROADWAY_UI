package com.alphacircle.vroadway.ui.home.category

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension.Companion.fillToConstraints
import androidx.constraintlayout.compose.Dimension.Companion.preferredWrapContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.data.PodcastWithExtraInfo
import com.alphacircle.vroadway.data.category.Asset
import com.alphacircle.vroadway.data.category.Content
import com.alphacircle.vroadway.data.category.LowLevelCategory
import com.alphacircle.vroadway.ui.components.ContentPopupMenu
import com.alphacircle.vroadway.ui.components.TicketGuide
import com.alphacircle.vroadway.ui.components.TicketGuideSlidePages
import com.alphacircle.vroadway.ui.home.PreviewContent
import com.alphacircle.vroadway.ui.theme.AppTheme
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.ui.theme.Keyline1
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.util.LockCategoryIconButton
import com.alphacircle.vroadway.util.fileSizeConverter
import com.alphacircle.vroadway.util.runningTimeConverter
import com.alphacircle.vroadway.util.viewModelProviderFactoryOf
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun VRCategory(
    lowLevelCategoryList: List<LowLevelCategory>,
    categoryId: Long,
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: (Long, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * CategoryEpisodeListViewModel requires the category as part of it's constructor, therefore
     * we need to assist with it's instantiation with a custom factory and custom key.
     */
    val viewModel: VRCategoryContentViewModel = viewModel(
        // We use a custom key, using the category parameter
        key = "category_list_$categoryId",
        factory = viewModelProviderFactoryOf { VRCategoryContentViewModel(categoryId) }
    )

    val viewState by viewModel.state.collectAsStateWithLifecycle()

    /**
     * TODO: reset scroll position when category changes
     */
    Log.println(Log.DEBUG, "VRCategory", "viewState.contents: ${viewState.contents.size}")
    Column(modifier = modifier) {
        CategoryPodcasts(lowLevelCategoryList, viewState.podCasts, viewModel, viewState.categoryIndex)
        ContentList(
            contents = viewState.contents,
            navigateToPlayer = navigateToPlayer,
            navigateToInfo = navigateToInfo
        )
    }
}

@Composable
private fun CategoryPodcasts(
    lowLevelCategories: List<LowLevelCategory>,
    topPodcasts: List<PodcastWithExtraInfo>,
    viewModel: VRCategoryContentViewModel,
    selectedIndex: Int
) {
    var lockCategoryGuideShow by remember { mutableStateOf(false) }
    var ticketGuideShow by remember { mutableStateOf(false) }

    if (lockCategoryGuideShow) {
        BottomSheetDialog(onDismissRequest = { lockCategoryGuideShow = false }) {
            TicketGuide(
                ticketGuideShow = { ticketGuideShow = true },
                onDismissRequest = { lockCategoryGuideShow = false })
        }
    }

    if (ticketGuideShow) {
        BottomSheetDialog(onDismissRequest = { ticketGuideShow = false }) {
            TicketGuideSlidePages { ticketGuideShow = false }
        }
    }

    CategoryPodcastRow(
        podcasts = topPodcasts,
        lowLevelCategories = lowLevelCategories,
        showBottomSheetDialog = {
            lockCategoryGuideShow = true
        },
        //viewModel::onTogglePodcastFollowed,
        modifier = Modifier.fillMaxWidth(),
        onCategorySelected = viewModel::onCategorySelected,
        selectedIndex = selectedIndex
    )
}

@Composable
private fun CategoryPodcastRow(
    podcasts: List<PodcastWithExtraInfo>,
    lowLevelCategories: List<LowLevelCategory>,
    showBottomSheetDialog: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCategorySelected: (Int, Int) -> Unit,
    selectedIndex: Int,
) {
    val lastIndex = lowLevelCategories.size - 1
//    var selectedIndex by remember { mutableStateOf(0) }
    val onSelectedIndexChange = { index: Int ->
//        selectedIndex = index
        onCategorySelected(lowLevelCategories[index].id, index)
    }
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = Keyline1, top = 8.dp, end = Keyline1, bottom = 24.dp)
    ) {
        itemsIndexed(items = lowLevelCategories) { index: Int,
                                                   (_, _, name, accessType, _, _, imageUrl): LowLevelCategory ->

            TopCategoryRowItem(
                podcastTitle = name,
                podcastImageUrl = imageUrl,
                index = index,
                selectedIndex = selectedIndex,
                onSelectedIndexChange = onSelectedIndexChange,
                isFollowed = accessType.toBoolean(),
                showBottomSheetDialog = { showBottomSheetDialog(imageUrl) },
                modifier = Modifier.width(128.dp)
            )

            if (index < lastIndex) Spacer(Modifier.width(24.dp))
        }
    }
}

@Composable
private fun TopCategoryRowItem(
    podcastTitle: String,
    index: Int,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    isFollowed: Boolean,
    modifier: Modifier = Modifier,
    showBottomSheetDialog: () -> Unit,
    podcastImageUrl: String? = null,
) {
    Column(
        modifier.semantics(mergeDescendants = true) {}
    ) {
        Box(
            Modifier
                .clickable { onSelectedIndexChange(index) }
                .fillMaxWidth()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            if (podcastImageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(podcastImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            4.dp,
                            if (index == selectedIndex) VroadwayColors.primary else Color.Transparent,
                            MaterialTheme.shapes.medium
                        )
                        .clip(MaterialTheme.shapes.medium),
                )
            }

            LockCategoryIconButton(
                onClick = showBottomSheetDialog,
                isLock = true, //isFollowed,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Text(
            text = podcastTitle,
            color = if (index == selectedIndex) VroadwayColors.primary else VroadwayColors.onSurface,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ContentList(
    contents: List<Content>,
    asset: List<Asset> = listOf(),
    navigateToPlayer: (String) -> Unit,
    navigateToInfo: (Long, Int) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(contents, key = { it.id }) { item ->
            ContentListItem(
                content = item,
                asset = asset,
                onClick = navigateToPlayer,
                infoOnClick = navigateToInfo,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
fun ContentListItem(
    content: Content,
    asset: List<Asset>,
    onClick: (String) -> Unit,
    infoOnClick: (Long, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var popupExpanded by remember { mutableStateOf(false) }
    val onPopupDismiss = { value: Boolean -> popupExpanded = value }

    ConstraintLayout(/*modifier = modifier.clickable { onClick(content.title) }*/) {
        val (
            divider, episodeTitle, podcastTitle, image, playIcon,
            date, addPlaylist, overflow, dropdown
        ) = createRefs()

        // If we have an image Url, we can show it using Coil
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(content.bannerUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(104.dp, 64.dp)
                .clip(MaterialTheme.shapes.large)
                .constrainAs(image) {
                    start.linkTo(parent.start, 24.dp)
                    top.linkTo(parent.top, 16.dp)
                },
        )

        val titleImageBarrier = createBottomBarrier(podcastTitle, image)

        Image(
            imageVector = Icons.Rounded.PlayCircleFilled,
            contentDescription = stringResource(R.string.cd_play),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false, radius = 24.dp)
                ) { /* TODO */ }
                .size(48.dp)
                .padding(6.dp)
                .semantics { role = Role.Button }
                .constrainAs(playIcon) {
                    centerVerticallyTo(image)
                    centerHorizontallyTo(image)
                }
//                .graphicsLayer(alpha = 0.8f)
        )

        Text(
            text = content.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = KoreanTypography.subtitle2,
            modifier = Modifier
                .constrainAs(episodeTitle) {
                    start.linkTo(image.end, 16.dp)
                    top.linkTo(parent.top, 24.dp)
                    height = preferredWrapContent
                }
                .width(180.dp)
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = runningTimeConverter(content.runningTime),
//                when {
//                    episode.duration != null -> {
//                        // If we have the duration, we combine the date/duration via a
//                        // formatted string
//                        stringResource(
//                            R.string.episode_date_duration,
//                            MediumDateFormatter.format(episode.published),
//                            episode.duration.toMinutes().toInt()
//                        )
//                    }
//                    // Otherwise we just use the date
//                    else -> MediumDateFormatter.format(episode.published)
//                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = EnglishTypography.caption,
                modifier = Modifier.constrainAs(date) {
                    centerVerticallyTo(image)
                    start.linkTo(episodeTitle.start)
                    top.linkTo(episodeTitle.bottom)
                    width = preferredWrapContent
                }
            )
        }

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = fileSizeConverter(content.runningTime), //TODO change this asset.size
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = EnglishTypography.caption,
                modifier = Modifier.constrainAs(podcastTitle) {
                    start.linkTo(date.end, 8.dp)
                    top.linkTo(date.top)
                    height = preferredWrapContent
                    width = preferredWrapContent
                }
            )
        }

        IconButton(
            onClick = { onPopupDismiss(true) },
            modifier = Modifier.constrainAs(overflow) {
                end.linkTo(parent.end, 8.dp)
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.cd_more)
            )
        }

        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(image.bottom, 16.dp)
                centerHorizontallyTo(parent)
                width = fillToConstraints
            }
        )

        ContentPopupMenu(
            expanded = popupExpanded,
            onPopupDismiss = onPopupDismiss,
            infoOnClick = { infoOnClick(content.categoryId.toLong(), content.sorting-1) },
            modifier = Modifier
                .constrainAs(dropdown) {
                    end.linkTo(parent.end, 16.dp)
                }
                .fillMaxWidth()
        )
    }
}

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
}

@Preview(showSystemUi = true)
@Composable
fun PreviewEpisodeListItem() {
    AppTheme {
        ContentListItem(
            content = PreviewContent[0],
            asset = listOf(),
            onClick = { },
            infoOnClick = { l: Long, i: Int -> },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

