package xyz.midnight233.emocio.components.compose

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(icon: ImageVector, contentDescription: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        elevation = FloatingActionButtonDefaults.elevation(8.dp, 0.dp, 4.dp, 6.dp),
        backgroundColor = defaultColors.surface,
        contentColor = defaultColors.foreground,
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(icon, contentDescription)
    }
}