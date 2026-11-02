package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ParchmentColorScheme = lightColorScheme(
    primary = LapisDeepBlue,
    onPrimary = ParchmentIvory,
    primaryContainer = LapisRoyalBlue,
    onPrimaryContainer = RegistanGoldBright,
    secondary = BurntTerracotta,
    onSecondary = ParchmentIvory,
    secondaryContainer = ParchmentAged,
    onSecondaryContainer = ManuscriptInk,
    tertiary = SamarkandTurquoise,
    onTertiary = ParchmentIvory,
    background = ParchmentIvory,
    onBackground = ManuscriptInk,
    surface = ParchmentWarm,
    onSurface = ManuscriptInk,
    surfaceVariant = ParchmentAged,
    onSurfaceVariant = ManuscriptMutedInk,
    outline = RegistanGold,
    error = CrimsonSeal,
    onError = ParchmentIvory
)

@Composable
fun CenturiesTheme(
    textSizeScale: Float = 1.0f,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ParchmentColorScheme,
        typography = buildCenturiesTypography(textSizeScale),
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    CenturiesTheme(textSizeScale = 1.0f, content = content)
}
