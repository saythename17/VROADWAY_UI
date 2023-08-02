package com.alphacircle.vroadway.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alphacircle.vroadway.R

@Composable
fun Splash(navigateToHome: () -> Unit) {
    LottieImage(navigateToHome)
}

@Composable
fun LottieImage(navigateToHome: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.app_title_logo_lottie)
    )
    val lottieAnimatable = rememberLottieAnimatable()
    val lottieAnimationState =
        animateLottieCompositionAsState(composition = composition)

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 120),
            initialProgress = 0f
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.size(300.dp).clickable(
                interactionSource = remember { MutableInteractionSource() } ,
                indication = null
            ) { navigateToHome() }
        )
    }
    if(lottieAnimationState.isAtEnd && lottieAnimationState.isPlaying) {
        navigateToHome()
    }
}

