package xyz.midnight233.emocio.implementation

import xyz.midnight233.emocio.implementation.EmocioBackend.exists
import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.StateType
import litterae.compose.Composition
import litterae.content.Artifact
import litterae.content.Segment
import litterae.runtime.Instance

object EmocioRuntime {
    lateinit var artifact: Artifact
    var readyExit = false

    val currentSegment get() = artifact.segments
        .find { it.identifier == Instance.current.currentSegmentIdentifier }!!
    val currentScene get() = currentSegment.scenes[Instance.current.currentSceneIndex.toInt()]

    fun daemonThread() {
        // Wait for instance to come up
        while (!Instance.instanceReady.get()) Thread.onSpinWait()
        // Profile initialization.
        if (!Instance.current.exists()) {
            Instance.current.run(artifact.initializer)
            Instance.current.save()
        } else Instance.current.load()
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