package com.androidatlas.notes.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AtlasColorScheme = lightColorScheme(
    primary = AtlasColors.Primary,
    onPrimary = AtlasColors.SurfaceLight,
    primaryContainer = AtlasColors.PrimaryLight,
    onPrimaryContainer = AtlasColors.TextDark,

    secondary = AtlasColors.Secondary,
    onSecondary = AtlasColors.SurfaceLight,
    secondaryContainer = AtlasColors.SecondaryLight,
    onSecondaryContainer = AtlasColors.TextDark,

    tertiary = AtlasColors.Info,
    onTertiary = AtlasColors.SurfaceLight,
    tertiaryContainer = Color(0xFFD1E7FF),
    onTertiaryContainer = AtlasColors.TextDark,

    background = AtlasColors.BackgroundLight,
    onBackground = AtlasColors.TextDark,

    surface = AtlasColors.SurfaceLight,
    onSurface = AtlasColors.TextDark,
    surfaceVariant = Color(0xFFF3F4F6),
    onSurfaceVariant = AtlasColors.TextMedium,

    outline = AtlasColors.BorderLight,
    outlineVariant = Color(0xFFF0F0F0),

    error = AtlasColors.Error,
    onError = AtlasColors.SurfaceLight,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = AtlasColors.Error,

    scrim = Color.Black
)

@Composable
fun AtlasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AtlasColorScheme,
        typography = AtlasTypography,
        content = content
    )
}
