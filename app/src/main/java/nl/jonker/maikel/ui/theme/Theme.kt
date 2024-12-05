package nl.jonker.maikel.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    secondary = DarkPurple,
    onSecondary = Color.White,
    background = LightBlueBackground,
    onBackground = BlackText,
    surface = SurfaceVariantColor,
    onSurface = GrayText,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.Black,
    secondary = DarkPurple,
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = DarkPurple,
    onSurface = GrayText,
)

@Composable
fun PokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}