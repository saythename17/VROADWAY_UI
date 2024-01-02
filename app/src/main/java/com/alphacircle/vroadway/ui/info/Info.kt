package com.alphacircle.vroadway.ui.info

import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.util.viewModelProviderFactoryOf

@Composable
fun Info(
    onBackPress: () -> Unit,
    categoryId: Long,
    index: Int
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(VroadwayColors.surface)
    ) {
        val (
            backButton, bgImage, infoContainer
        ) = createRefs()

        val viewModel: ContentInfoViewModel = viewModel(factory = viewModelProviderFactoryOf { ContentInfoViewModel(categoryId, index) })
        val viewState by viewModel.state.collectAsStateWithLifecycle()

        AsyncImage(
            model = viewState.content?.bannerUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(bgImage) {
                    start.linkTo(parent.start)
                },
//            colorFilter = ColorFilter.tint(colorResource(id = R.color.dark_filter), BlendMode.Darken)
        )

        BackButton(
            onBackPress = onBackPress,
            modifier = Modifier.constrainAs(backButton) {
                start.linkTo(parent.start, 8.dp)
                top.linkTo(parent.top, 32.dp)
            }
        )



        InfoSheet(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .constrainAs(infoContainer) {
                    top.linkTo(bgImage.bottom, (-32).dp)
                }
                .fillMaxHeight(),
            viewState = viewState
        )


    }

}

@Composable
fun BackButton(onBackPress: () -> Unit, modifier: Modifier) {
    IconButton(
        onClick = onBackPress,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.cd_back),
            tint = Color.White
        )
    }
}

@Composable
fun InfoSheet(modifier: Modifier, viewState: ContentInfoViewState) {
    Column(
        modifier = modifier.background(VroadwayColors.surface)
    ) {
        Column(modifier = Modifier
            .padding(24.dp, 16.dp)
            ) {
            Text(
                text = viewState.content?.title?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = viewState.content?.title?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { heading() },
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )

            val description = viewState.content?.description?: ""
            Text(
                text = Html.fromHtml(description).toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 40.dp, 0.dp, 40.dp),
                style = KoreanTypography.body2,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InfoPreview() {
    Info({}, 1L, 0)
}