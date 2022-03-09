package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmocioNarrative(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 20.sp,
        letterSpacing = 1.sp,
        modifier = modifier
    )
}

@Composable
fun EmocioEmphasize(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 20.sp,
        letterSpacing = 1.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

@Composable fun Modifier.EmocioNormalPadding(extra: Dp = 0.dp): Modifier = this.padding(
    top = 4.dp + extra,
    bottom = 2.dp + extra,
    start = 4.dp + extra,
    end = 4.dp + extra
)