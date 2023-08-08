package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.theme.EnglishTypography

@Composable
fun TopBar(onBackPress: () -> Unit, appBarColor: Color, title: String?) {
    TopAppBar(
        backgroundColor = appBarColor,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 32.dp, 0.dp, 8.dp),
        title = {
            //TopAppBar Content
            Box(Modifier.height(32.dp)) {
                //Navigation Icon
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(onClick = onBackPress) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.cd_back)
                            )
                        }
                    }
                }

                //Title
                Row(
                    Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = title ?: "",
                        style = EnglishTypography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar(onBackPress = { /*TODO*/ }, appBarColor = Color.DarkGray, title = "Title")
}