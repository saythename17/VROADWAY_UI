package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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


@Composable
fun TicketGuide(
    ticketGuideShow: () -> Unit,
    onDismissRequest: () -> Unit
) {
    LockCategoryGuide(
        R.drawable.ic_ticket,
        stringResource(id = R.string.bottom_sheet_guide_ticket),
        stringResource(
            id = R.string.bottom_sheet_guide_ticket_sub
        ),
        stringResource(id = R.string.ticket_button_text),
        isTicketGuide = true,
        ticketGuideShow = ticketGuideShow,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun IAPGuide(
    onIAP: () -> Unit,
    onDismissRequest: () -> Unit
) {
    LockCategoryGuide(
        R.drawable.ic_money_bag,
        stringResource(id = R.string.bottom_sheet_guide_iap),
        stringResource(
            id = R.string.bottom_sheet_guide_iap_sub
        ),
        buttonText = "결제",
        onIAP = onIAP,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun BothGuide(
    onIAP: () -> Unit,
    ticketGuideShow: () -> Unit,
    onDismissRequest: () -> Unit
) {
    LockCategoryGuide(
        R.drawable.ic_ticket,
        stringResource(id = R.string.bottom_sheet_guide_both),
        stringResource(
            id = R.string.bottom_sheet_guide_both_sub
        ),
        buttonText = stringResource(id = R.string.both_button_text_no),
        buttonTextRight = stringResource(id = R.string.both_button_text_yes),
        isTicketGuide = true,
        onIAP = onIAP,
        ticketGuideShow = ticketGuideShow,
        onDismissRequest = onDismissRequest
    )
}

@Composable
fun LockCategoryGuide(
    iconId: Int,
    mainText: String,
    subText: String,
    buttonText: String,
    buttonTextRight: String? = null,
    isTicketGuide: Boolean = false,
    onIAP: () -> Unit = {},
    ticketGuideShow: () -> Unit = {},
    onDismissRequest: () -> Unit,
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
        if (isTicketGuide) TicketPopupIcon(
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

        when {
            buttonTextRight == null -> GradientButton(
                text = buttonText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                onClick = {
                    onDismissRequest()
                    if (isTicketGuide) ticketGuideShow()
                    else onIAP()
                }
            )

            else -> Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                GradientButton(
                    text = buttonText,
                    modifier = Modifier
                        .weight(1.0f, true)
                        .widthIn(min = 0.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    onClick = {
                        onDismissRequest()
                        onIAP()
                    }
                )
                GradientButton(
                    text = buttonTextRight,
                    modifier = Modifier
                        .weight(1.0f, true)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    onClick = {
                        onDismissRequest()
                        ticketGuideShow()
                    }
                )
            }
        }

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
        IAPGuide(onIAP = { /*TODO*/ }) {}

        TicketGuide(ticketGuideShow = { /*TODO*/ }) {}
    }
}

@Preview
@Composable
fun PreviewGuideBoth() {
    BothGuide(onIAP = { /*TODO*/ }, ticketGuideShow = { /*TODO*/ }) {}
}