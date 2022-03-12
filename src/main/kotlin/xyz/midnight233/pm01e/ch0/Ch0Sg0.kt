package xyz.midnight233.pm01e.ch0

import litterae.content.Segment

object Ch0Sg0 : Segment({
    val sceneIntro by scene("Introduction") {
        immediate {
            narrator.."你在颠簸中醒来"
        }
    }
})