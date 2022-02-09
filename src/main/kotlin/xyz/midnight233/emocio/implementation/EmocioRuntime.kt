package xyz.midnight233.emocio.implementation

import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.compose.Composition
import xyz.midnight233.litterae.content.Action
import xyz.midnight233.litterae.content.Artifact
import xyz.midnight233.litterae.content.Segment
import xyz.midnight233.litterae.runtime.Frontend
import xyz.midnight233.litterae.runtime.Instance
import java.util.concurrent.atomic.AtomicBoolean

object EmocioRuntime {
    lateinit var artifact: Artifact
    var newProfile = false
    var readyExit = false

    val currentSegment get() = artifact.segments
        .find { it.identity == Instance.current.currentSegmentIdentifier }!!
    val currentScene get() = currentSegment.scenes
        .find { it.objectIdentifier == Instance.current.currentSceneIdentifier }!!

    fun daemonThread() {
        // Profile initialization.
        if (newProfile) {
            Instance.current.run(artifact.initializer)
            Instance.current.save()
        } else {
            Instance.current.load()
        }
        // Content initialization.
        artifact.segments.forEach(Segment::build)
        // Content loop.
        while (!readyExit) {
            currentScene.rebuild()
            // Do immediate block if available.
            if (currentScene.immediateEvent != null) currentScene.immediateEvent!!(Composition)
            // Or ask for action.
            else {
                EmocioState.actions.value = currentScene.actions
                EmocioState.stateType.value = StateType.Action
            }
            EmocioFrontend.waitForResponse()
            EmocioState.actionChoice.value.lambda(Composition)
            // Save the session before continuing.
            Instance.current.save()
        }
    }
}