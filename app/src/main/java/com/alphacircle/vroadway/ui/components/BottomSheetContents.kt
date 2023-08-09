package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
//        Icon(
//            Icons.Default.Close,
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(16.dp, 0.dp)
//                .clickable {  }
//        )
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

@Composable
fun LockCategoryGuide(
    iconId: Int,
    mainText: String,
    subText: String,
    buttonText: String,
    needTicketPopup: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 24.dp)
            .background(Color.White, RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp)),
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
//        Icon(
//            Icons.Default.Close,
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(16.dp, 0.dp)
//                .clickable {  }
//        )
        if (needTicketPopup) TicketPopupIcon(
            modifier = Modifier
                .align(Alignment.End)
                .padding(120.dp, 0.dp)
        )
        Image(painterResource(id = iconId), null, Modifier.size(80.dp))
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = mainText, color = Color.Black, style = KoreanTypography.h3)
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = subText,
            color = Color.Gray,
            style = KoreanTypography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        GradientButton(
            text = "보내기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun TicketPopupIcon(modifier: Modifier) {
    ConstraintLayout(modifier = modifier.clickable { }) {
        val (icon, popup) = createRefs()
        var popupExpended by remember { mutableStateOf(false) }
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier
                .constrainAs(icon) { }
                .clickable { popupExpended = true }
        )

        DropdownMenu(
            expanded = popupExpended,
            onDismissRequest = { popupExpended = false },
            modifier = Modifier
                .wrapContentSize()
                .background(
                    VroadwayColors.primary
                )
                .clip(VroadwayShapes.medium)

        ) {
            DropdownMenuItem(onClick = { popupExpended = false }) {
                Text(
                    text = stringResource(id = R.string.bottom_sheet_guide_ticket_info),
                    style = KoreanTypography.overline,
                    color = Color.White
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGuide() {
    Column() {
        LockCategoryGuide(
            R.drawable.ic_money_bag,
            stringResource(id = R.string.bottom_sheet_guide_iap),
            stringResource(
                id = R.string.bottom_sheet_guide_iap_sub
            ),
            buttonText = "결제"
        )

        LockCategoryGuide(
            R.drawable.ic_ticket,
            stringResource(id = R.string.bottom_sheet_guide_ticket),
            stringResource(
                id = R.string.bottom_sheet_guide_ticket_sub
            ),
            buttonText = "티켓 등록",
            true
        )
    }
}