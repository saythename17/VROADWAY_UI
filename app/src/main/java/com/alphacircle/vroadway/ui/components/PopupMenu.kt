package com.alphacircle.vroadway.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class DropdownMenuItem(
    val icon: ImageVector,
    val text: String,
    val contentDescription: String,
    val onClick: () -> Unit
)

@Composable
fun CategoryItemDropdownMenu(
    onItemClick: (Int) -> Unit,
    items: List<DropdownMenuItem>
) {
    var expanded by remember { mutableStateOf(true) }

    val onItemClick = { value: Boolean -> expanded = value }

    val menu = remember { mutableListOf<DropdownMenuItem>() }
    items.forEach { menu.add(it) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            menu.clear()
        }
    ) {
        Column {
            items.forEach { item ->
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Divider(color = MaterialTheme.colors.onSurface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryMenu() {
    val items = listOf(
        com.alphacircle.vroadway.ui.components.DropdownMenuItem(
            icon = Icons.Filled.Info,
            text = "Info",
            contentDescription = "Info",
            onClick = {}
        ),
        com.alphacircle.vroadway.ui.components.DropdownMenuItem(
            icon = Icons.Filled.Share,
            text = "Share",
            contentDescription = "Share",
            onClick = {}
        ),
        com.alphacircle.vroadway.ui.components.DropdownMenuItem(
            icon = Icons.Filled.Delete,
            text = "Delete",
            contentDescription = "Delete",
            onClick = {}
        )
    )

    CategoryItemDropdownMenu(
        onItemClick = { },
        items = items
    )
}
