package xyz.midnight233.pm01e.ch0

import xyz.midnight233.litterae.compose.Composition.narrate
import xyz.midnight233.litterae.content.Segment

object Ch0Sg0 : Segment({
    val sceneIntro by scene("Introduction") {
        var immediateFinished by mark()
        val noteOne by note(
            title = "Just a note",
            content = "Good to go!"
        )
        if (!immediateFinished) {
            "Test, test, test".narrate()
            immediateFinished = true
        }
        action(
            name = "Test",
            description = "Test test"
        ) {
            "Hey!".narrate()
            "One, two or three?".choose {
                "One".bind {
                    "One!".narrate()
                }
                "Two".bind {
                    "Two!".narrate()
                }
                "Three".bind {
                    "Three!".narrate()
                    val more = "Any more?".ask()
                    more.narrate()
                }
            }
        }
        action(
            name = "Toggle",
            description = "Toggle!!!"
        ) {
            noteOne.available = !noteOne.available
        }
    }
})