package com.sjm.bankapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sjm.bankapp.R

val OutfitFont = FontFamily(
    Font(R.font.outfit_regular),
    Font(R.font.outfit_light, FontWeight.Light),
    Font(R.font.outfit_medium, FontWeight.Medium),
    Font(R.font.outfit_semibold, FontWeight.Normal),
    Font(R.font.outfit_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = OutfitFont,

    body1 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 18.sp),
    body2 = TextStyle(fontWeight = FontWeight.Normal, fontSize = 18.sp),
    button = TextStyle(fontWeight = FontWeight.Normal, fontSize = 18.sp, color = Black),
    /* Other default text styles to override
    h1 = TextStyle(fontFamily = FontFamily.Default),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
        */
)

//val btnTextSize = 18.sp
//val subtitle = 24.sp
