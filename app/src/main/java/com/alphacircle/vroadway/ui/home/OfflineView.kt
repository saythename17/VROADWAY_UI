package com.alphacircle.vroadway.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.GradientButton
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayShapes

@Composable
fun OfflineView(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GuideView()
        RetryButton(onRetry)
    }

}


@Composable
fun GuideView() {
    Image(
        painter = painterResource(id = R.drawable.ic_cloud_night), contentDescription = null,
        modifier = Modifier.size(80.dp)
    )
    Text(
        text = stringResource(id = R.string.offline_main),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = KoreanTypography.subtitle1,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Text(
        text = stringResource(id = R.string.offline_sub),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = KoreanTypography.body2,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RetryButton(onRetry: () -> Unit) {
    GradientButton(
        modifier = Modifier.padding(8.dp, 4.dp),
        text = stringResource(id = R.string.retry_connect_button_text),
        shape = VroadwayShapes.small,
        textStyle = KoreanTypography.overline,
        onClick = onRetry
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewOfflineView() {
    OfflineView{}
}