package xyz.midnight233.emocio.components.segment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.midnight233.emocio.components.compose.RightPanel
import xyz.midnight233.emocio.components.compose.EmocioButton
import xyz.midnight233.emocio.components.compose.EnumChooserButton
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.content.Action
import xyz.midnight233.litterae.content.ActionCategory

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
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(current) {
                EmocioButton(
                    onClick = {
                        EmocioState.actionChoice.value = it
                        EmocioState.stateType.value = StateType.Response
                    },
                ) {
                    Column {
                        if (it.description != null) {
                            Text(
                                text = it.name,
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = it.description,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        } else {
                            Text(
                                text = it.name,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
    LaunchedEffect(EmocioState.actions.value, category) {
        val filtered = EmocioState.actions.value.filter { it.category == category }
        if (filtered != current) current = filtered
    }
}