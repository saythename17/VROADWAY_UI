/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alphacircle.vroadway.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alphacircle.vroadway.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold)
)

private val GmarketSans = FontFamily(
    Font(R.font.gmarketsans_light, FontWeight.Light),
    Font(R.font.gmarketsans_medium, FontWeight.Medium),
    Font(R.font.gmarketsans_bold, FontWeight.Bold),
)

val EnglishTypography = Typography(
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 96.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 117.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 60.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 73.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 48.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 59.sp
    ),
    h4 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 30.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 37.sp
    ),
    h5 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 29.sp
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 17.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp
    ),
    body2 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = Montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    overline = TextStyle(
        fontFamily = Montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    ),
)

val KoreanTypography = Typography(
    h1 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 96.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 117.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 60.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 73.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 37.sp
    ),
    h5 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 29.sp
    ),
    h6 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 17.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 16.sp,
        letterSpacing = 0.15.sp
    ),
    body2 = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    overline = TextStyle(
        fontFamily = GmarketSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 12.sp,
        letterSpacing = 1.sp
    ),
)
