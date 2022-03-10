package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.midnight233.emocio.components.RowLambda
import xyz.midnight233.emocio.implementation.EmocioBackend
import javax.swing.JOptionPane

@Composable fun EmocioButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: RowLambda
) {
    Button(
        shape = RoundedCornerShape(50),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0.97f, 0.97f, 0.97f),
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 2.dp,
            hoveredElevation = 4.dp,
            pressedElevation = 0.dp
        ),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 0.dp
        ),
        modifier = modifier
    ) {
        Row(content = content)
    }
}

@Composable fun EmocioTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    EmocioButton(
        onClick = onClick,
        modifier = Modifier.height(32.dp).then(modifier)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}