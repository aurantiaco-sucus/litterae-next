package xyz.midnight233.emocio.components.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import xyz.midnight233.emocio.components.LeftPanel
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.content.Note
import xyz.midnight233.litterae.content.NoteCategory

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