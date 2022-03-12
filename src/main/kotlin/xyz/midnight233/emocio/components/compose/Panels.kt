package xyz.midnight233.emocio.components.compose

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable fun BoxScope.ContentPanel(content: BoxLambda) {
    Box(modifier = Modifier
        .align(Alignment.Center)
        .widthIn(min = 800.dp)
        .fillMaxHeight()
        .fillMaxWidth(0.6f)
    ) {
        content()
    }
}