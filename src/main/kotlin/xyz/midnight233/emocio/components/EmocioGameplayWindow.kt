package xyz.midnight233.emocio.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType

@Composable fun EmocioGameplayWindow() {
    EmocioState.run {
        stateType = remember { mutableStateOf(StateType.Action) }
        journalSize = remember { mutableStateOf(0) }
        journal = remember { mutableStateListOf() }
        candidates = remember { mutableStateOf(listOf("1", "2", "3", "4", "5", "6")) }
        stringPredicate = remember { mutableStateOf({ true }) }
        choicesPredicate = remember { mutableStateOf({ true }) }
        intRange = remember { mutableStateOf(0..1) }
        choice = remember { mutableStateOf(0) }
        choices = remember { mutableStateOf(listOf()) }
        response = remember { mutableStateOf("") }
    }
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            EmocioJournalView()
            LeftPanel(true, "Notebook") {}
            RightPanel(true, "Operation") {}
            var state by EmocioState.stateType
            Button(onClick = {
                val next = if (state.ordinal == StateType.values().lastIndex) 0 else state.ordinal + 1
                state = StateType.values()[next]
            }, modifier = Modifier.align(Alignment.BottomEnd))
            { Text("Switch!") }
        }
    }
}