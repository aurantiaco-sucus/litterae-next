package xyz.midnight233.emocio.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import xyz.midnight233.emocio.components.segment.EmocioActionView
import xyz.midnight233.emocio.components.segment.EmocioJournalView
import xyz.midnight233.emocio.components.segment.EmocioNotebookView
import xyz.midnight233.emocio.implementation.EmocioRuntime
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.content.Action
import xyz.midnight233.litterae.content.ActionCategory

@Composable fun EmocioGameplayWindow() {
    EmocioState.run {
        stateType = remember { mutableStateOf(StateType.Response) }
        journalSize = remember { mutableStateOf(0) }
        notebookSize = remember { mutableStateOf(0) }
        choiceCandidates = remember { mutableStateOf(emptyList()) }
        stringPredicate = remember { mutableStateOf({ true }) }
        choicesPredicate = remember { mutableStateOf({ true }) }
        intRange = remember { mutableStateOf(0..1) }
        choice = remember { mutableStateOf(0) }
        multiChoice = remember { mutableStateOf(emptyList()) }
        stringResponse = remember { mutableStateOf("") }
        actions = remember { mutableStateOf(emptyList()) }
        actionChoice = remember { mutableStateOf(Action("", ActionCategory.Context, "") {}) }
    }
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            EmocioJournalView()
            EmocioActionView()
            EmocioNotebookView()
            var state by EmocioState.stateType
        }
    }
    SideEffect {
        Thread(EmocioRuntime::daemonThread).start()
    }
}