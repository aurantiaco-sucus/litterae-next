package xyz.midnight233.pm01e

import litterae.content.Artifact
import xyz.midnight233.pm01e.ch0.Ch0Sg0

val pm01artifact get() = Artifact(
    identifier = "pm01_next",
    title = "Dome 41",
    author = "Midnight233",
    version = "InDev",
    segments = listOf(
        Ch0Sg0
    ),
    initializer = {
        currentSegmentIdentifier = Ch0Sg0::class.qualifiedName!!
        currentSceneIndex = "0"
    }
)