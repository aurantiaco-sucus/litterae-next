package xyz.midnight233.emocio.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.BottomPopupPanel(available: Boolean, composable: ColumnLambda) {
    AnimatedVisibility(
        visible = available,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface(
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier
                .fillMaxSize()
        ) {}
    }
    AnimatedVisibility(
        visible = available,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 },
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
            modifier = Modifier
                .size(600.dp, 300.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) { composable() }
        }
    }
}