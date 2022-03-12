package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import javax.swing.JFrame

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FrameWindowScope.DecoratedFrame(
    title: String,
    onClose: () -> Unit,
    content: ComposableLambda
) {
    EmocioMaterial {
        Surface(
            border = BorderStroke(0.5.dp, Color.Gray),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                WindowDraggableArea(
                    modifier = Modifier.combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                        onDoubleClick = {
                            if (window.extendedState == JFrame.MAXIMIZED_BOTH) {
                                window.extendedState = JFrame.NORMAL
                            } else window.extendedState = JFrame.MAXIMIZED_BOTH
                        }
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth().background(defaultColors.surface)) {
                        Text(
                            text = title.uppercase(),
                            fontSize = 12.sp,
                            letterSpacing = 4.sp,
                            modifier = Modifier.align(Alignment.Center).padding(top = 5.dp, bottom = 4.dp)
                        )
                        Row(
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            IconButton(
                                onClick = {
                                    window.extendedState = JFrame.ICONIFIED
                                },
                                modifier = Modifier.scale(0.5f).size(32.dp)
                            ) { Icon(Icons.Filled.KeyboardArrowDown, "Minimize") }
                            IconButton(
                                onClick = onClose,
                                modifier = Modifier.scale(0.5f).size(32.dp)
                            ) { Icon(Icons.Default.Close, "Close") }

                        }
                    }
                }
                content()
            }
        }
    }
}