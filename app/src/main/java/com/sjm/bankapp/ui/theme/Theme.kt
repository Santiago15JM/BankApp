package com.sjm.bankapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = LightGreen,
    primaryVariant = Green,
    secondary = LightRed,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val DarkColorPalette = darkColors(
    primary = HighBlue,
    primaryVariant = LowBlue,
    secondary = LightRed,
)

@Composable
fun BankAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val sysUi = rememberSystemUiController()
    val colors: Colors
    if (darkTheme) {
        colors = DarkColorPalette
        sysUi.setSystemBarsColor(Color(0xFF222222))
    } else {
        colors = LightColorPalette
        sysUi.setSystemBarsColor(Color(0xFFE6E6E6))
    }
    MaterialTheme(
        colors = colors, typography = Typography, shapes = Shapes, content = content
    )
}
