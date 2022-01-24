package xyz.midnight233.litterae.runtime

abstract class Frontend {
    abstract fun showNotification(content: String)
    abstract fun showNarration(content: String)
    abstract fun showSpeech(subject: String, content: String)
    abstract fun showPrompt(content: String)

    abstract fun inputString(validator: (String) -> Boolean): String
    abstract fun inputRangedInt(range: IntRange): Int
    abstract fun inputBoolean(): Boolean
    abstract fun inputChoice(choices: List<String>): String
    abstract fun inputMultipleChoices(choices: List<String>, validator: (List<String>) -> Boolean): List<String>
    abstract fun requestContinue()

    companion object {
        lateinit var current: Frontend
    }
}