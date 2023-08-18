package com.alphacircle.vroadway.ui.account

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.NoListView
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.theme.EnglishTypography

@Composable
fun PurchasedHistory(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    val purchasedList = listOf<String>("", "")

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.account_purchased_history))
        },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            when(purchasedList.size) {
                0 -> NoListView(text = stringResource(id = R.string.no_purchased_history))
                else -> PurchasedList()
            }
        }
    }
}

@Composable
fun PurchasedList() {
    PurchasedItem(navigateToArticle = {}, isTicket = false)
    PurchasedItem(navigateToArticle = {}, isTicket = false)
}

@Composable
fun PurchasedItem(
    navigateToArticle: (String) -> Unit,
    isTicket: Boolean
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { })
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            CategoryInfo(stringResource(id = R.string.home_my_collections), "2023-05-19 10:01:19")
        }

        CategoryImage(Modifier.padding(16.dp), "https://kpopanswers.com/wp-content/uploads/2023/04/how-old-are-newjeans-members.jpg")
    }
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
        thickness = 0.3.dp
    )
}

@Composable
private fun CategoryImage(
    modifier: Modifier = Modifier,
    podcastImageUrl: String? = null,
) {
    Column(
        modifier.semantics(mergeDescendants = true) {}
    ) {
        Box(
            Modifier.align(Alignment.CenterHorizontally)
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
                        .size(120.dp)
                        .clip(MaterialTheme.shapes.medium),
                )
            }
        }
    }
}

@Composable
fun CategoryInfo(title: String, time: String) {

    Text(
        text = time,
        style = EnglishTypography.caption,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
    Text(
        text = title,
        style = EnglishTypography.subtitle1,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
fun PreviewPurchasedItem() {
    PurchasedItem(navigateToArticle = {}, isTicket = false)
}

@Preview
@Composable
fun PreviewPurchasedHistory() {
    PurchasedHistory(onBackPress = {})
}