package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import xyz.midnight233.emocio.components.RightPanel
import xyz.midnight233.emocio.components.compose.EnumChooserButton
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.content.Action
import xyz.midnight233.litterae.content.ActionCategory
import xyz.midnight233.litterae.content.Note
import xyz.midnight233.litterae.content.NoteCategory
import javax.swing.Box

@Composable fun BoxScope.EmocioActionView() {
    val categoryState = remember { mutableStateOf(ActionCategory.Context) }
    val category by categoryState
    var current by remember { mutableStateOf(emptyList<Action>()) }
    RightPanel(
        available = EmocioState.stateType.value == StateType.Action,
        header = {
            EnumChooserButton(categoryState)
        }
    ) {
        LazyColumn {
            items(current) {
                Button(
                    onClick = {
                        EmocioState.stateType.value = StateType.Response
                    }
                ) {
                    Row {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold
                        )
                        if (it.description != null) {
                            Spacer(Modifier.height(8.dp))
                            EmocioContentText(it.description)
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(EmocioState.actions.value) {
        val filtered = EmocioState.actions.value.filter { it.category == category }
        if (filtered != current) current = filtered
    }
}