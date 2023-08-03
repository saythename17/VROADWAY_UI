package com.alphacircle.vroadway.ui.account

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Payment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.MenuIcon
import com.alphacircle.vroadway.ui.components.MenuItem
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.home.discover.Discover
import com.alphacircle.vroadway.ui.theme.EnglishTypography

@Composable
fun Account(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface
    val appBarColor = surfaceColor.copy(alpha = 0.87f)

    val isSignIn = true

    Scaffold(
        topBar = {
            TopBar(onBackPress, appBarColor, stringResource(R.string.cd_account))
        },
        bottomBar = {
            when(isSignIn){
                true -> DeleteButton()
                false -> {}
            }

        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {

            when (isSignIn) {
                true -> AccountComponents()
                false -> SignIn()
            }
        }
    }
}

@Composable
fun AccountComponents() {
    Profile()
//    Spacer(modifier = Modifier.height(16.dp))

    MenuItem(
        menuIcon = MenuIcon.ImageVectorIcon(Icons.Default.Payment),
        name = R.string.account_purchased_history,
        onClick = {}
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
fun DeleteButton() {
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
            modifier = Modifier.clickable {  }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccountPreview() {
    Account {}
}