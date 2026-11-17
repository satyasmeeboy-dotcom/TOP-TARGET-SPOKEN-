package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = EmeraldOnPrimary,
    primaryContainer = EmeraldPrimaryContainer,
    onPrimaryContainer = EmeraldOnPrimaryContainer,
    secondary = LeafSecondary,
    onSecondary = LeafOnSecondary,
    secondaryContainer = LeafSecondaryContainer,
    onSecondaryContainer = LeafOnSecondaryContainer,
    tertiary = WarmGoldTertiary,
    onTertiary = WarmGoldOnTertiary,
    tertiaryContainer = WarmGoldContainer,
    onTertiaryContainer = WarmGoldOnTertiaryContainer,
    background = MintSurface,
    onBackground = DeepForestText,
    surface = PureWhite,
    onSurface = DeepForestText,
    surfaceVariant = MintSurfaceVariant,
    onSurfaceVariant = MutedForestText,
    outline = SoftBorderGreen,
    error = ErrorCrimson,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkEmeraldPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = EmeraldPrimaryContainer,
    secondary = LeafSecondaryContainer,
    onSecondary = EmeraldOnPrimaryContainer,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = EmeraldPrimaryContainer,
    tertiary = WarmGoldContainer,
    onTertiary = WarmGoldOnTertiaryContainer,
    tertiaryContainer = WarmGoldTertiary,
    onTertiaryContainer = PureWhite,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkMutedText,
    outline = DarkPrimaryContainer,
    error = ErrorContainerLight,
    errorContainer = OnErrorContainerLight,
    onErrorContainer = ErrorContainerLight
)

val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun TopTargetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Keep intentional Emerald Green & White brand identity as default
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
