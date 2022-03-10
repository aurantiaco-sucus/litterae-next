package xyz.midnight233.emocio.components.compose

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(icon: ImageVector, contentDescription: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        elevation = FloatingActionButtonDefaults.elevation(8.dp, 0.dp, 4.dp, 6.dp),
        backgroundColor = Color(0.97f, 0.97f, 0.97f),
        contentColor = Color.Black,
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(icon, contentDescription)
    }
}