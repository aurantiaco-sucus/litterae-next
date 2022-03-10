package xyz.midnight233.emocio.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.midnight233.emocio.components.compose.ActionButton
import xyz.midnight233.emocio.components.compose.EmocioNarrative

@Composable inline fun BoxScope.LeftPanel(
    available: Boolean,
    crossinline header: ComposableLambda,
    crossinline composable: ColumnLambda
) = _EmocioUiSidePanelImplNext(
    available = available,
    composable = composable,
    openFabIcon = Icons.Default.KeyboardArrowRight,
    transitionLambda = { -it / 2 },
    panelAlignment = Alignment.CenterStart,
    openFabOffsetY = -15f,
    panelShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    panelHeader = header
)

@Composable inline fun BoxScope.RightPanel(
    available: Boolean,
    crossinline header: ComposableLambda,
    crossinline composable: ColumnLambda
) = _EmocioUiSidePanelImplNext(
    available = available,
    composable = composable,
    openFabIcon = Icons.Default.KeyboardArrowLeft,
    transitionLambda = { it / 2 },
    panelAlignment = Alignment.CenterEnd,
    openFabOffsetY = 15f,
    panelShape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
    panelHeader = header
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable inline fun BoxScope._EmocioUiSidePanelImplNext(
    available: Boolean,
    crossinline composable: ColumnLambda,
    openFabIcon: ImageVector,
    noinline transitionLambda: (Int) -> Int,
    panelAlignment: Alignment,
    openFabOffsetY: Float,
    panelShape: Shape,
    crossinline panelHeader: ComposableLambda,
) {
    var panelVisibility by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val fabOpacity by animateFloatAsState(if (available) 1f else 0f)

    val defaultAnimationSpec = TweenSpec<IntOffset>(
        durationMillis = 150,
        easing = LinearOutSlowInEasing
    )

    ActionButton(
        icon = openFabIcon,
        contentDescription = "Open panel",
        modifier = Modifier
            .align(panelAlignment)
            .offset(x = openFabOffsetY.dp)
            .alpha(fabOpacity)
    ) {
        panelVisibility = !panelVisibility
    }

    AnimatedVisibility(
        visible = panelVisibility && available,
        enter = slideInHorizontally(
            initialOffsetX = transitionLambda,
            animationSpec = defaultAnimationSpec
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = transitionLambda,
            animationSpec = defaultAnimationSpec
        ) + fadeOut(),
        modifier = Modifier.align(panelAlignment)
    ) {
        Card(
            elevation = 0.dp,
            border = BorderStroke(width = 0.5.dp, color = Color.LightGray),
            backgroundColor = Color.White,
            shape = panelShape,
            modifier = Modifier
                .heightIn(min = 400.dp, max = 800.dp)
                .fillMaxHeight(0.75f)
                .widthIn(min = 300.dp, max = 500.dp)
                .fillMaxWidth(0.2f)
        ) {
            Column(Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Box(modifier = Modifier
                        .align(Alignment.Center)
                    ) {
                        panelHeader()
                    }
                    IconButton(
                        onClick = {
                            panelVisibility = !panelVisibility
                        },
                        modifier = Modifier
                            .align(panelAlignment)
                            .scale(0.75f)
                    ) { Icon(Icons.Default.Close, "Close panel") }
                }
                composable()
            }
        }
    }
}