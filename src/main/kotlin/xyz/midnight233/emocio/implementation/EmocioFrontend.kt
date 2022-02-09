package xyz.midnight233.emocio.implementation

import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.emocio.stateful.JournalEntry
import xyz.midnight233.emocio.stateful.JournalEntryType
import xyz.midnight233.emocio.stateful.StateType
import xyz.midnight233.litterae.runtime.Frontend

object EmocioFrontend : Frontend() {
    fun waitForResponse() {
        while (EmocioState.stateType.value != StateType.Response) Thread.onSpinWait()
    }

    override fun showNotification(content: String) {
        EmocioState.journal += JournalEntry(
            type = JournalEntryType.Notification,
            content = content
        )
    }

    override fun showNarration(content: String) {
        EmocioState.journal += JournalEntry(
            type = JournalEntryType.Narration,
            content = content
        )
        EmocioState.journalSize.value++
        EmocioState.stateType.value = StateType.Continue
    }

    override fun showSpeech(subject: String, content: String) {
        EmocioState.journal += JournalEntry(
            type = JournalEntryType.Speech,
            content = "$subject::$content"
        )
        EmocioState.stateType.value = StateType.Continue
    }

    override fun showPrompt(content: String) {
        EmocioState.journal += JournalEntry(
            type = JournalEntryType.Prompt,
            content = content
        )
    }

    override fun inputString(validator: (String) -> Boolean): String {
        EmocioState.stringPredicate.value = validator
        EmocioState.stateType.value = StateType.String
        waitForResponse()
        return EmocioState.stringResponse.value
    }

    override fun inputRangedInt(range: IntRange): Int {
        EmocioState.intRange.value = range
        EmocioState.stateType.value = StateType.RangedInt
        waitForResponse()
        return EmocioState.stringResponse.value.toInt()
    }

    override fun inputBoolean(): Boolean {
        EmocioState.stateType.value = StateType.Boolean
        waitForResponse()
        return EmocioState.stringResponse.value == "yes"
    }

    override fun inputChoice(choices: List<String>): String {
        EmocioState.choiceCandidates.value = choices
        EmocioState.stateType.value = StateType.Choose
        waitForResponse()
        return choices[EmocioState.choice.value]
    }

    override fun inputMultipleChoices(choices: List<String>, validator: (List<String>) -> Boolean): List<String> {
        EmocioState.choiceCandidates.value = choices
        EmocioState.stateType.value = StateType.MultiChoose
        waitForResponse()
        return EmocioState.multiChoice.value.map { choices[it] }
    }

    override fun requestContinue() {
        EmocioState.stateType.value = StateType.Continue
        waitForResponse()
    }
}