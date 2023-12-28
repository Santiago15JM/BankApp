package com.sjm.bankapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightGreen = Color(0xFF76EA78)
val Green = Color(0xFF68D66D)
val LightRed = Color(0xFFEA6161)
val Red = Color(0xFFD32D20)

val HighBlue = Color(0xFF00D5C3)
val LowBlue = Color(0xFF009F9F)

val SurfaceLight = Color(0xFFE6E6E6)
val SurfaceDark = Color(0xFF222222)

val StrokeLight = Color(0xFFCCCCCC)
val StrokeDark = Color(0xFF767676)

val Black = Color(0xFF000000)
val LightSecondaryBtnColor = Color(0xFFDDDDDD)
val DarkSecondaryBtnColor = Color(0xFF767676)

//@Composable
//fun surfaceColor(): Color {
//    return if (isSystemInDarkTheme()) SurfaceDark
//    else SurfaceLight
//}

@Composable
fun strokeColor(): Color {
    return if (isSystemInDarkTheme()) StrokeDark
    else StrokeLight
}

@Composable
fun accentColor(): Color {
    return if (isSystemInDarkTheme()) HighBlue
    else LightGreen
}

@Composable
fun secondaryBtnColor(): Color {
    return if (isSystemInDarkTheme()) DarkSecondaryBtnColor
    else LightSecondaryBtnColor
}

@Composable
fun emphasisTextColor(): Color {
    return if (isSystemInDarkTheme()) LowBlue
    else Green
}