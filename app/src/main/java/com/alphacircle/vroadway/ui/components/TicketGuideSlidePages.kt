package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TicketGuideSlidePages() {
    val state = rememberPagerState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
        HorizontalPager(count = 4, state = state) { page ->
            when (page) {
                0 -> TicketGuidePage(
                    R.drawable.ic_web,
                    stringResource(id = R.string.bottom_sheet_guide_ticket_register_1),
                    true
                )

                1 -> TicketGuidePage(
                    R.drawable.ic_ticket,
                    stringResource(id = R.string.bottom_sheet_guide_ticket_register_2),
                )

                2 -> TicketGuidePage(
                    R.drawable.ic_mobile,
                    stringResource(id = R.string.bottom_sheet_guide_ticket_register_3),
                )

                3 -> TicketGuidePage(
                    R.drawable.ic_vr,
                    stringResource(id = R.string.bottom_sheet_guide_ticket_register_4),
                )
            }
        }

        DotIndicator(
            totalDots = 4,
            selectedIndex = state.currentPage,
            selectedColor = VroadwayColors.primary,
            unSelectedColor = Color.Gray
        )

        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ){
            GradientButton(
                text = "보내기",
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .widthIn(min = 0.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            )
            GradientButton(
                text = "보내기",
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun TicketGuidePage(
    iconId: Int,
    mainText: String,
    needTicketPopup: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        if (needTicketPopup) TicketPopupIcon(
            modifier = Modifier
                .align(Alignment.End)
                .padding(120.dp, 0.dp)
        )
        Image(painterResource(id = iconId), null, Modifier.size(80.dp))
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = mainText,
            color = Color.Black,
            style = KoreanTypography.subtitle2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun LinkToWebPopup(modifier: Modifier) {
    ConstraintLayout(modifier = modifier.clickable { }) {
        val (icon, popup) = createRefs()
        var popupExpended by remember { mutableStateOf(false) }
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = VroadwayColors.primary,
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
                    text = stringResource(id = R.string.bottom_sheet_guide_ticket_register_popup),
                    style = KoreanTypography.overline,
                    color = Color.White
                )
            }
        }
    }
}

