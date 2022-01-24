package xyz.midnight233.emocio.implementation

import xyz.midnight233.litterae.runtime.Frontend

object EmocioFrontend : Frontend() {
    override fun showNotification(content: String) {
        TODO("Not yet implemented")
    }

    override fun showNarration(content: String) {
        TODO("Not yet implemented")
    }

    override fun showSpeech(subject: String, content: String) {
        TODO("Not yet implemented")
    }

    override fun showPrompt(content: String) {
        TODO("Not yet implemented")
    }

    override fun inputString(validator: (String) -> Boolean): String {
        TODO("Not yet implemented")
    }

    override fun inputRangedInt(range: IntRange): Int {
        TODO("Not yet implemented")
    }

    override fun inputBoolean(): Boolean {
        TODO("Not yet implemented")
    }

    override fun inputChoice(choices: List<String>): String {
        TODO("Not yet implemented")
    }

    override fun inputMultipleChoices(choices: List<String>, validator: (List<String>) -> Boolean): List<String> {
        TODO("Not yet implemented")
    }

    override fun requestContinue() {
        TODO("Not yet implemented")
    }
}