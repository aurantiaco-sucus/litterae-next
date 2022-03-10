import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import xyz.midnight233.emocio.components.window.EmocioGameplayWindow
import xyz.midnight233.emocio.components.window.EmocioLauncherWindow
import xyz.midnight233.emocio.components.compose.DecoratedFrame
import xyz.midnight233.emocio.implementation.EmocioBackend
import xyz.midnight233.emocio.implementation.EmocioDefault
import xyz.midnight233.emocio.implementation.EmocioRuntime
import xyz.midnight233.emocio.stateful.EmocioState

@OptIn(ExperimentalFoundationApi::class)
fun main(args: Array<String>) {
    println("\nLitterae.Next/Emocio Desktop, Development Version\n")

    EmocioState.emocioArgs = args
    EmocioState.emocioArtifacts = EmocioDefault.artifacts
    EmocioRuntime.artifact = EmocioState.emocioArtifacts[0]
    EmocioBackend.composeBackendInit()

    application {
        EmocioState.gameReady = remember { mutableStateOf(false) }
        Window(
            onCloseRequest = ::exitApplication,
            title = "Emocio Launcher",
            visible = !EmocioState.gameReady.value,
            state = rememberWindowState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(300.dp, 500.dp)
            ),
            undecorated = true,
            transparent = true
        ) {
            DecoratedFrame(
                title = "launcher",
                onClose = {
                    exitApplication()
                }
            ) { EmocioLauncherWindow() }
        }

        val state = rememberWindowState(
            size = DpSize(1000.dp, 600.dp),
            position = WindowPosition(Alignment.Center)
        )
        Window(
            onCloseRequest = {
                EmocioRuntime.readyExit = true
                exitApplication()
            },
            visible = EmocioState.gameReady.value,
            title = "Emocio",
            state = state,
            undecorated = true,
            transparent = true,
        ) {
            DecoratedFrame(
                title = "emocio",
                onClose = { exitApplication() }
            ) { EmocioGameplayWindow() }
        }
    }
}
