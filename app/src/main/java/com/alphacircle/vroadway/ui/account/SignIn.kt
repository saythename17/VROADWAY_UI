package com.alphacircle.vroadway.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.GradientButton

@Composable
fun SignIn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Logo()
        Spacer(modifier = Modifier.height(40.dp))
        GoogleLoginButton()
        KakaoLoginButton()
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(R.drawable.app_title_logo),
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier
            .heightIn(max = 56.dp)
    )
}

@Composable
fun GoogleLoginButton() {
    /* TODO  Change this button to Google Login Button */
    GradientButton(text = "Google로 로그인")
}

@Composable
fun KakaoLoginButton() {
    /* TODO  Change this button to Kakao Login Button */
    GradientButton(text = "Kakao로 로그인")
}


@Preview(showSystemUi = true)
@Composable
fun SignInPreview() {
    SignIn()
}