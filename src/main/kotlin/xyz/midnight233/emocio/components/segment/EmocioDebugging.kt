package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable fun ShouldNeverHappen(content: String) {
    Column {
        Text(
            text = "In short, you met something that should never happen! ;)",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "In long: $content"
        )
        Text(
            text = "Report that to litterae-next/emocio project or... the developer of this particular product?",
            fontWeight = FontWeight.Bold
        )
    }
}