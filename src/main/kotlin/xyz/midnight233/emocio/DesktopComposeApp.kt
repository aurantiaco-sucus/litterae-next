import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import xyz.midnight233.emocio.components.EmocioGameplayWindow
import xyz.midnight233.emocio.components.EmocioLauncherWindow
import xyz.midnight233.emocio.implementation.EmocioBackend
import xyz.midnight233.emocio.implementation.EmocioDefault
import xyz.midnight233.emocio.implementation.EmocioRuntime
import xyz.midnight233.emocio.stateful.EmocioState

fun main(args: Array<String>) {
    println("\nLitterae.Next/Emocio Desktop, Development Version\n")

    EmocioState.emocioArgs = args
    EmocioState.emocioArtifacts = EmocioDefault.artifacts
    EmocioRuntime.artifact = EmocioState.emocioArtifacts[0]

    application {
        if (!EmocioState.emocioReady) Window(
            onCloseRequest = ::exitApplication,
            title = "Emocio Launcher",
            state = rememberWindowState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(300.dp, 500.dp)
            )
        ) {
            EmocioLauncherWindow()
        }
        Window(
            onCloseRequest = ::exitApplication,
            visible = EmocioState.emocioReady,
            title = "Emocio"
        ) {
            EmocioGameplayWindow()
        }
    }
}
