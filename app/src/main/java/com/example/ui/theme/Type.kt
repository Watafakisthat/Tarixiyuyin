package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val CinzelDisplayFamily = FontFamily(
    Font(R.font.cinzel, FontWeight.Normal),
    Font(R.font.cinzel, FontWeight.Bold)
)

val LoraBodyFamily = FontFamily(
    Font(R.font.lora, FontWeight.Normal),
    Font(R.font.lora, FontWeight.Medium),
    Font(R.font.lora, FontWeight.Bold)
)

fun buildCenturiesTypography(scale: Float = 1.0f): Typography {
    val s = scale.coerceIn(0.85f, 1.3f)
    return Typography(
        displayLarge = TextStyle(
            fontFamily = CinzelDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (34 * s).sp,
            lineHeight = (40 * s).sp
        ),
        headlineLarge = TextStyle(
            fontFamily = CinzelDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (26 * s).sp,
            lineHeight = (32 * s).sp
        ),
        headlineMedium = TextStyle(
            fontFamily = CinzelDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (21 * s).sp,
            lineHeight = (27 * s).sp
        ),
        titleLarge = TextStyle(
            fontFamily = CinzelDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (18 * s).sp,
            lineHeight = (24 * s).sp
        ),
        titleMedium = TextStyle(
            fontFamily = LoraBodyFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (16 * s).sp,
            lineHeight = (22 * s).sp
        ),
        bodyLarge = TextStyle(
            fontFamily = LoraBodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (15 * s).sp,
            lineHeight = (22 * s).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = LoraBodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = (13.5f * s).sp,
            lineHeight = (19 * s).sp
        ),
        labelLarge = TextStyle(
            fontFamily = CinzelDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (13 * s).sp,
            lineHeight = (18 * s).sp
        ),
        labelMedium = TextStyle(
            fontFamily = LoraBodyFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (11.5f * s).sp,
            lineHeight = (15 * s).sp
        )
    )
}

val Typography = buildCenturiesTypography(1.0f)
