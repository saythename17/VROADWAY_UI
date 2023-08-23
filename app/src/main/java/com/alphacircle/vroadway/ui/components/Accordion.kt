package com.alphacircle.vroadway.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.data.AccordionModel
import com.alphacircle.vroadway.data.Board
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors

@Composable
fun Accordion(
    modifier: Modifier = Modifier,
    board: Board,
    boardType: String,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        AccordionHeader(
            title = board.title,
            isExpanded = expanded,
            boardType = boardType,
            onTapped = { expanded = !expanded })
        AnimatedVisibility(visible = expanded) {
            Surface(
                border = BorderStroke(1.dp, Gray),
                elevation = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                AccordionContent(board.description)
            }
        }
    }
}

@Composable
private fun AccordionHeader(
    title: String = "",
    sub: String = "",
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {},
    boardType: String = "notice"
) {
    val degrees = if (isExpanded) 180f else 0f

    Surface(
        border = BorderStroke(1.dp, VroadwayColors.onSurface.copy(alpha = 0.3f)),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Surface(
//                shape = CircleShape,
//                color = VroadwayColors.primary,
//                modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
//            ) {
            if (boardType != "policy") {
                Icon(
                    if (boardType == "notice") Icons.Filled.Warning else Icons.Filled.CircleNotifications,
                    contentDescription = "arrow-down",
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp),
                    tint = VroadwayColors.primary
                )
            }

//            }

            Column(Modifier.weight(1f)) {
//                Text(
//                    sub,
//                    style = KoreanTypography.caption,
//                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
////                    color = VroadwayColors.onSurface.copy(alpha = 0.3f)
//                )
                Text(
                    title,
                    style = KoreanTypography.subtitle2,
//                    color = VroadwayColors.onSurface
                )
            }

            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "arrow-down",
                modifier = Modifier.rotate(degrees),
//                tint = VroadwayColors.onSurface
            )
        }
    }
}

@Composable
private fun AccordionContent(
    content: String
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
    ) {
        Text(
            content,
            Modifier
                .fillMaxWidth()
                .padding(32.dp),
            style = KoreanTypography.body2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAccordion() {
    val board = Board(
        title = "[필독] 사용 설명서 - I. 사전 체크사항",
        sorting = 0,
        description = "Checklist 1\n" +
                "VROADWAY는 network 미연결 시 동작하지 않습니다.\n" +
                "VROADWAY를 실행했는데, 만일 콘텐츠가 전혀 보이지 않는다면 Network 연결 상태를 확인하세요.\n" +
                "\n" +
                "Checklist 2\n" +
                "VROADWAY는 ios 14.0 이상에서 정상 동작하며, 이보다 낮은 version의 0인 경우 0S 업데이트가 필요합니다.\n" +
                "\n" +
                "Checklist 3\n" +
                "VROADWAY는 저전력 모드에서는 화면 프레임이 끊기는 현상 이 발생합니다. 저전력 모드를 해제하고 사용하시기 바랍니다.\n" +
                "\n" +
                "Checklist 4\n" +
                "원활한 VROADWAY 사용을 위해서는 세로 화면 방향 고정을 off 해야 합니다. 세로 화면 방향 고정이 on 되어 있는 경우에는 (스마트폰의 회전이 감지되지 않아) 재생 모드로 진입이 되지 않 습니다.\n" +
                "\n" +
                "Checklist 5\n" +
                "자동 밝기를 on 하고 사용하는 경우, 휴대폰을 VR HMD 안에 삽입하면 주변 밝기가 어둡다고 인식하여 화면이 어두워 집니다.\n" +
                "쾌적한 VR 관람을 위해서는 자동 밝기를 off하고 사용해 주시기 바랍니다."
    )
    Accordion(board = board, boardType = "")
}

@Preview
@Composable
fun PreviewAccordionHeader() {
    AccordionHeader()
}

/**
 * Refer: https://medium.com/@eozsahin1993/accordion-menu-in-jetpack-compose-32151adf6d80
 */