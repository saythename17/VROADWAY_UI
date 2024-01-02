package com.alphacircle.vroadway.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Payment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.AcAlertDialog
import com.alphacircle.vroadway.ui.components.MenuIcon
import com.alphacircle.vroadway.ui.components.MenuItem
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.theme.EnglishTypography
import com.alphacircle.vroadway.ui.theme.KoreanTypography

@Composable
fun Account(
    onBackPress: () -> Unit,
    navigateToPurchasedHistory: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    var openAlertDialog by remember { mutableStateOf(false) }
    val onOpenAlertDialog = { value: Boolean -> openAlertDialog = value }

    // TODO: Receive the signIn status from AppLocalState
    val isSignIn = true

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.cd_account))
        },
        bottomBar = {
            when(isSignIn){
                true -> DeleteButton(onOpenAlertDialog)
                false -> {}
            }

        }
    ) {
        Column {
            when (isSignIn) {
                true -> Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(it)
                ) {
                    AccountComponents(navigateToPurchasedHistory)
                }

                false -> SignIn()
            }

            when {
                openAlertDialog -> {
                    DeleteUserAlertDialog(
                        onDismissRequest = { onOpenAlertDialog(false) },
                        onConfirmation = {
                            onOpenAlertDialog(false)
                            // TODO: Add logic here to handle confirmation.
                            println("Confirmation registered")
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AccountComponents(navigateToPurchasedHistory: () -> Unit) {
    Profile()

    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Payment),
        name = R.string.account_purchased_history,
        onClick = navigateToPurchasedHistory
    )
    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Logout),
        name = R.string.account_logout,
        onClick = {}
    )
}

@Composable
fun Profile() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val profileImage = false
        val modifier = Modifier
            .padding(16.dp)
            .size(160.dp)
            .clip(CircleShape)
        when (profileImage) {
            true -> AsyncImage(
                model = "https://avatars.githubusercontent.com/u/29122714?v=4",
                contentDescription = null,
                modifier = modifier,
                contentScale = ContentScale.Crop,
            )

            false -> Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = modifier,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
            )
        }

        Text(
            text = "alphacircle@google.com",
            style = EnglishTypography.body1,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 32.dp)
        )
    }
}

@Composable
fun DeleteButton(onOpenAlertDialog: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Delete Account",
            style = EnglishTypography.body1,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.clickable { onOpenAlertDialog(true) }
        )
    }
}

@Composable
fun DeleteUserAlertDialog (
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AcAlertDialog(
        onDismissRequest = { onDismissRequest() },
        onConfirmation = { onConfirmation() },
        dialogTitle = stringResource(id = R.string.account_delete_alert_dialog_title),
        dialogText = stringResource(id = R.string.account_delete_alert_dialog_text),
        confirmText = stringResource(id = R.string.account_delet_alert_dialog_confirm),
        dismissText = null,
    )
}

@Preview(showSystemUi = true)
@Composable
fun AccountPreview() {
    Account({}, {})
}