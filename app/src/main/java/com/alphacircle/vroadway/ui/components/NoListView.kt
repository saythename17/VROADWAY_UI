package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.theme.AppTheme
import com.alphacircle.vroadway.ui.theme.KoreanTypography

@Composable
fun NoListView(text: String) {
    AppTheme {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            textAlign = TextAlign.Center,
            style = KoreanTypography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewNoListView() {
    NoListView(text = stringResource(id = R.string.no_download_list))
}