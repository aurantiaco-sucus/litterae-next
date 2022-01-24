package xyz.midnight233.emocio.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable inline fun BoxScope.LeftPanel(
    available: Boolean,
    title: String,
    crossinline composable: ColumnLambda
) = _EmocioUiSidePanelImpl(
    available = available,
    composable = composable,
    openFabIcon = Icons.Default.KeyboardArrowRight,
    closeFabIcon = Icons.Default.Close,
    transitionLambda = { -it / 2 },
    panelAlignment = Alignment.CenterStart,
    closeFabOffsetX = -10f,
    openFabOffsetY = -15f,
    panelShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    panelTitle = title
)

@Composable inline fun BoxScope.RightPanel(
    available: Boolean,
    title: String,
    crossinline composable: ColumnLambda
) = _EmocioUiSidePanelImpl(
    available = available,
    composable = composable,
    openFabIcon = Icons.Default.KeyboardArrowLeft,
    closeFabIcon = Icons.Default.Close,
    transitionLambda = { it / 2 },
    panelAlignment = Alignment.CenterEnd,
    closeFabOffsetX = 10f,
    openFabOffsetY = 15f,
    panelShape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
    panelTitle = title
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable inline fun BoxScope._EmocioUiSidePanelImpl(
    available: Boolean,
    crossinline composable: ColumnLambda,
    openFabIcon: ImageVector,
    closeFabIcon: ImageVector,
    noinline transitionLambda: (Int) -> Int,
    panelAlignment: Alignment,
    closeFabOffsetX: Float,
    openFabOffsetY: Float,
    panelShape: Shape,
    panelTitle: String,
) {
    var panelVisibility by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    var fabMouseOver by remember { mutableStateOf(false) }
    val fabOpacity by animateFloatAsState(if (fabMouseOver) 1f else 0.33f)
    AnimatedVisibility(
        visible = panelVisibility && available,
        enter = slideInHorizontally(initialOffsetX = transitionLambda) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = transitionLambda) + fadeOut(),
        modifier = Modifier
            .align(panelAlignment)
    ) {
        Card(
            elevation = 4.dp,
            backgroundColor = Color.White,
            shape = panelShape,
            modifier = Modifier
                .heightIn(min = 400.dp)
                .fillMaxHeight(0.75f)
                .widthIn(min = 250.dp)
                .fillMaxWidth(0.2f)
        ) {
            Column(Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    FloatingActionButton(
                        onClick = { panelVisibility = false },
                        modifier = Modifier.align(panelAlignment).offset(x = closeFabOffsetX.dp, y = (-10).dp)
                    ) { Icon(closeFabIcon, "Close panel") }
                    Text(
                        text = panelTitle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                composable()
            }
        }
    }
    AnimatedVisibility(
        visible = !panelVisibility && available,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .align(panelAlignment)
            .offset(x = openFabOffsetY.dp)
            .alpha(fabOpacity)
            .pointerMoveFilter(
                onEnter = { coroutine.launch { fabMouseOver = true }; false },
                onExit = { coroutine.launch { fabMouseOver = false }; false })
    ) {
        FloatingActionButton(onClick = {
            panelVisibility = !panelVisibility
            fabMouseOver = false
        }) { Icon(openFabIcon, "Open panel") }
    }
}