package com.alphacircle.vroadway.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors

sealed class MenuIcon {
    data class DrawableResIcon(@DrawableRes val iconId: Int) : MenuIcon()
    data class ImageVectorIcon(val imageVector: ImageVector) : MenuIcon()
}

@Composable
fun MenuItem(
    menuIcon: MenuIcon,
//    @StringRes iconDesc: Int?,
    @StringRes name: Int,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colors.onSurface
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(16.dp)

        ) {
            Spacer(modifier = Modifier.width(16.dp))
            menuIcon?.let { icon ->
                when (icon) {
                    is MenuIcon.DrawableResIcon -> {
                        Icon(
                            painterResource(id = icon.iconId),
//                                contentDescription = stringResource(id = iconDesc),
                            null,
                            modifier = Modifier.size(24.dp),
                            tint = color
                        )
                    }

                    is MenuIcon.ImageVectorIcon -> {
                        Icon(
                            imageVector = icon.imageVector,
//                                contentDescription = stringResource(id = iconDesc),
                            null,
                            modifier = Modifier.size(24.dp),
                            tint = color
                        )
                    }
                }
            }
            Text(
                text = stringResource(id = name),
                style = EnglishTypography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp, 0.dp),
                color = color
            )
        }
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            thickness = 0.3.dp
        )
    }
}

@Preview("Settings Text Component")
@Composable
fun PreviewSettingItem() {
    MenuItem(
        name = R.string.cd_settings,
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Filled.Settings),
//        iconDesc = R.string.cd_settings,
        onClick = {}
    )
}