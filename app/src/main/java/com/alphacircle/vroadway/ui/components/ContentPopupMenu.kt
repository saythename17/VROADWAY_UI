package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

data class PopupMenuItem(
    val icon: ImageVector,
    val text: String,
    val contentDescription: String,
    val onClick: () -> Unit
)

@Composable
fun ContentPopupMenu(
    expanded: Boolean,
    onPopupDismiss: (Boolean) -> Unit,
    modifier: Modifier,
    infoOnClick: () -> Unit
) {
    var dialogVisible by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onPopupDismiss },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.TopEnd)
        ) {
            val items = listOf(
                PopupMenuItem(
                    icon = Icons.Filled.Info,
                    text = "Info",
                    contentDescription = "Info",
                    onClick = infoOnClick
                ),
                PopupMenuItem(
                    icon = Icons.Filled.Share,
                    text = "Share",
                    contentDescription = "Share",
                    onClick = {}
                ),
                PopupMenuItem(
                    icon = Icons.Filled.Delete,
                    text = "Delete",
                    contentDescription = "Delete",
                    onClick = { dialogVisible = true }
                )
            )
            items.forEachIndexed { _, item ->
                DropdownMenuItem(
                    onClick = {
                        onPopupDismiss(false)
                        item.onClick()
                    },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Column(
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        when {
                            item.text != "Delete" ->
                                Row {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.contentDescription,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = item.text,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp),
                                    )
                                }
                            item.text == "Delete" ->
                                Row {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.contentDescription,
                                        modifier = Modifier.padding(8.dp),
                                        tint = Color.Red
                                    )
                                    Text(
                                        text = item.text,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp),
                                        color = Color.Red
                                    )
                                }
                        }

//                            if(index < items.size-1) {
//                                Divider(
//                                    color = MaterialTheme.colors.onSurface,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(0.3.dp)
//                                )
//                            }
                    }
                }
            }
        }
        DeleteCancelableToastDialog(onDismissRequest = { dialogVisible = false }, show = dialogVisible)
    }
}

@Preview()
@Composable
fun PreviewContentPopupMenu() {
    ConstraintLayout {
        val ( dropdown) = createRefs()
        ContentPopupMenu(expanded = true, onPopupDismiss = {}, modifier = Modifier
                .constrainAs(dropdown) {
                    top.linkTo(parent.top, 16.dp)
                }
                .fillMaxWidth()
        ) {}
    }
}
