package xyz.midnight233.emocio.components.window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    Box(Modifier.fillMaxSize()) {
        EmocioJournalView()
        EmocioActionView()
        EmocioNotebookView()
    }
    SideEffect {
        Thread(EmocioRuntime::daemonThread).start()
    }
}