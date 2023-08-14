package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors
import com.alphacircle.vroadway.ui.theme.VroadwayShapes

@Preview(showBackground = true)
@Composable
fun InputFeedbackView() {
    var text by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp, 24.dp)
            .background(Color.White, RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(id = R.string.bottom_sheet_feedback),
            color = Color.Black,
            style = KoreanTypography.h5
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(
                    stringResource(id = R.string.bottom_sheet_feedback_hint),
                    style = KoreanTypography.body2
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(32.dp, 16.dp),
            shape = VroadwayShapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.White,
                cursorColor = VroadwayColors.primary,
            )
        )
        GradientButton(
            text = "보내기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}