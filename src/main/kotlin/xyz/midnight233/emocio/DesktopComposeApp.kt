import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import xyz.midnight233.emocio.implementation.EmocioRuntime
import xyz.midnight233.emocio.components.*
import xyz.midnight233.emocio.debugging.Placeholder
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import java.util.logging.Logger

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
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

fun main() {
    println("\nLitterae.Next/Emocio Desktop, Development Version\n")
    print("Initializing Emocio Runtime...")
    EmocioRuntime.run {
        artifact = Placeholder.emptyArtifact
    }
    println("Done.")
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
