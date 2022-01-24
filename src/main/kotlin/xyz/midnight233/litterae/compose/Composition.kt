package xyz.midnight233.litterae.compose

import xyz.midnight233.litterae.runtime.Frontend

object Composition {
    private val frontend get() = Frontend.current

    operator fun String.unaryMinus() {
        frontend.showNarration(this)
        frontend.requestContinue()
    }

    operator fun String.rangeTo(content: String) {
        frontend.showSpeech(this, content)
        frontend.requestContinue()
    }

    fun String.choose(builder: ChoiceBuilderScope.() -> Unit) {
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
        fun String.bind(content: CompositionLambda) {
            choices += this to content
        }
    }
}