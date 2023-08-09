package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.ui.theme.VroadwayShapes

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    inActive : Boolean = false
) {
    val gradient =
        Brush.linearGradient(
            0.0f to VroadwayColors.primary,
            0.6f to VroadwayColors.secondary,
            start = Offset.Zero,
            end = Offset.Infinite
        )

//    Brush.horizontalGradient(listOf(VroadwayColors.primary, VroadwayColors.secondary))
    Button(
        modifier = modifier,
        shape = VroadwayShapes.medium,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        val activeContainerModifier = Modifier
            .background(gradient)
            .then(modifier)
        val inActiveContainerModifier = Modifier
            .background(Color.Gray)
            .then(modifier)
        Box(
            modifier = if(inActive) inActiveContainerModifier else activeContainerModifier,
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                style = KoreanTypography.button,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGradientButton() {
    Column {
        GradientButton(
            text = "보내기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )
        GradientButton(
            text = "아니요(인앱 결제)",
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            inActive = true
        )
    }
}
