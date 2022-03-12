package xyz.midnight233.emocio.components.compose

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val defaultColors: EmocioColors = EmocioLight

sealed class EmocioColors(
    val background: Color,
    val foreground: Color,
    val surface: Color,
    val boundary: Color,
    val stroke: Color
)

object EmocioLight : EmocioColors(
    background = Color.White,
    foreground = Color.Black,
    surface = Color(0.97f, 0.97f, 0.97f),
    boundary = Color.DarkGray,
    stroke = Color.LightGray
)

object EmocioDark : EmocioColors(
    background = Color.Black,
    foreground = Color.White,
    surface = Color(0.2f, 0.2f, 0.2f),
    boundary = Color.LightGray,
    stroke = Color.Gray
)

@Composable fun EmocioMaterial(composable: ComposableLambda) {
    MaterialTheme(
        colors = Colors(
            primary = defaultColors.surface,
            primaryVariant = defaultColors.surface,
            secondary = defaultColors.surface,
            secondaryVariant = defaultColors.surface,
            background = defaultColors.background,
            surface = defaultColors.background,
            error = Color.Red,
            onPrimary = defaultColors.foreground,
            onSecondary = defaultColors.foreground,
            onBackground = defaultColors.foreground,
            onSurface = defaultColors.background,
            onError = defaultColors.foreground,
            isLight = defaultColors == EmocioLight
        ),
        content = composable
    )
}