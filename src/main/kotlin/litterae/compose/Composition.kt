package litterae.compose

import litterae.runtime.Frontend

object Composition {
    private val frontend get() = Frontend.current

    const val narrator = "#narrator#"

    operator fun String.rangeTo(content: String) {
        if (this == narrator) frontend.showNarration(content)
        else frontend.showSpeech(this, content)
        frontend.requestContinue()
    }

    operator fun String.rangeTo(builder: ChoiceBuilderScope.() -> Unit) {
        frontend.showPrompt(this)
        val choices = ChoiceBuilderScope().also(builder).choices
        val choice = frontend.inputChoice(choices.map { it.first })
        choices.find { it.first == choice }!!.second(Composition)
    }

    fun String.loop(builder: ChoiceBuilderScope.() -> Unit) {
        frontend.showPrompt(this)
        var looping = true
        val choices = ChoiceBuilderScope().also(builder).choices + ("(Cancel)" to composition { looping = false })
        while (looping) {
            val choice = frontend.inputChoice(choices.map { it.first })
            choices.find { it.first == choice }!!.second(Composition)
        }
    }

    class ChoiceBuilderScope {
        val choices = mutableListOf<Pair<String, CompositionLambda>>()
        operator fun String.rangeTo(content: CompositionLambda) {
            choices += this to content
        }
    }

    fun String.ask(): String {
        frontend.showPrompt(this)
        return frontend.inputString { true }
    }
}