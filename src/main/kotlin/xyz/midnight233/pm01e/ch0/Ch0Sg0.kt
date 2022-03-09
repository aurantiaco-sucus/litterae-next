package xyz.midnight233.pm01e.ch0

import xyz.midnight233.litterae.content.Segment

object Ch0Sg0 : Segment({
    val sceneIntro by scene("Introduction") {
        action("Invoke") {
            narrator.."Somewhere, you suddenly gained consciousness. *Absolutely nothing* is around you..."
            "Someone".."Wake up!"
            "\"Where am I?\", you asked from the bottom of heart...".. {
                "The bottom of the sea...".. {
                    narrator.."From forbidden pages of the past... Don't you know? You are... awaken from the sea..."
                }
                "The heart of darkness".. {
                    narrator.."From the very core of darkness... You are in no way a good one... The evil inside your " +
                        "heart woke you up..."
                }
            }
            "Your name, please?".ask()
        }
    }
})