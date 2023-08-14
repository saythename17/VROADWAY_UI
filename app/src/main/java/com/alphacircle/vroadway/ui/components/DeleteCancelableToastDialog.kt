package com.alphacircle.vroadway.ui.components

import android.annotation.SuppressLint
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.ui.theme.VroadwayShapes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun DeleteCancelableToastDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    val coroutineScope = rememberCoroutineScope()
    if (show) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = properties,
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
            Row(
                modifier = Modifier
                    .background(Color.DarkGray, VroadwayShapes.large)
                    .padding(16.dp, 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "VR 콘텐츠가 삭제됩니다.",
                    style = KoreanTypography.body2,
                    modifier = Modifier.weight(0.7f, true),
                    color = Color.White
                )

                Text(
                    text = "실행취소",
                    style = KoreanTypography.subtitle2,
                    modifier = Modifier
                        .weight(0.3f, true)
                        .padding(32.dp, 0.dp, 0.dp, 0.dp)
                        .clickable { },
                    textAlign = TextAlign.End,
                    color = VroadwayColors.primary,
                )

            }
        }
    }

    LaunchedEffect(show) {
        coroutineScope.launch {
            delay(3500L)
            onDismissRequest()
            /* TODO ToastDialog 사라진 후 삭제 */
        }
    }
}

@Preview
@Composable
fun PreviewToastDialog() {
    var show by remember { mutableStateOf(true) }
    DeleteCancelableToastDialog(onDismissRequest = { show = false }, show = show)
}
