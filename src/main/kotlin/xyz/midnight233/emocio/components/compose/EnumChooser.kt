package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

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
        modifier = modifier
    ) {
        Text(value.name)
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