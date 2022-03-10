package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
inline fun <reified T: Enum<T>> EnumChooserButton(
    selection: MutableState<T>,
    modifier: Modifier = Modifier
) {
    var value by selection
    var menuExpanded by remember { mutableStateOf(false) }
    OutlinedButton(
        onClick = {
            menuExpanded = true
        },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = defaultColors.foreground
        ),
        border = BorderStroke(0.5.dp, defaultColors.stroke),
        modifier = Modifier.height(32.dp).then(modifier)
    ) {
        EmocioButtonCaption(value.name)
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = {
                menuExpanded = false
            }
        ) {
            enumValues<T>().forEach {
                DropdownMenuItem(
                    onClick = {
                        value = it
                        menuExpanded = false
                    }
                ) {
                    Text(it.name)
                }
            }
        }
    }
}