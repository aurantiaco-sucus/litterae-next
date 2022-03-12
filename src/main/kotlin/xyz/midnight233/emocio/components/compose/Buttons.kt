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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun EmocioButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: RowLambda
) {
    Button(
        shape = RoundedCornerShape(50),
        onClick = onClick,
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

@Composable fun EmocioButtonCaption(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
}