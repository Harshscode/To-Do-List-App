package com.taskflow.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    outlineVariant = OutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryContainer,
    onPrimary = OnPrimaryContainer,
    primaryContainer = Primary,
    onPrimaryContainer = OnPrimary,
    secondary = SecondaryContainer,
    onSecondary = OnSecondaryContainer,
    secondaryContainer = Secondary,
    onSecondaryContainer = OnSecondary,
    tertiary = TertiaryContainer,
    onTertiary = OnTertiaryContainer,
    tertiaryContainer = Tertiary,
    onTertiaryContainer = OnTertiary,
    error = ErrorContainer,
    onError = OnErrorContainer,
    errorContainer = Error,
    onErrorContainer = OnError,
    background = OnBackground,
    onBackground = Background,
    surface = OnSurface,
    onSurface = Surface,
    surfaceVariant = OnSurfaceVariant,
    onSurfaceVariant = SurfaceVariant,
    outline = OutlineVariant,
    outlineVariant = Outline
)

@Composable
fun TaskFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
