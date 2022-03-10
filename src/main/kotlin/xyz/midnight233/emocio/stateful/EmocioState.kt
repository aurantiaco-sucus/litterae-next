package xyz.midnight233.emocio.stateful

import androidx.compose.runtime.MutableState
import xyz.midnight233.litterae.content.Action
import xyz.midnight233.litterae.content.Artifact

object EmocioState {
    // Gameplay content states
    lateinit var journalSize: MutableState<Int>
    lateinit var notebookSize: MutableState<Int>

    // Gameplay content non-states
    val journal = mutableListOf<JournalEntry>()

    // Gameplay interaction states
    lateinit var stateType: MutableState<StateType>
    lateinit var choiceCandidates: MutableState<List<String>>
    lateinit var stringPredicate: MutableState<(String) -> Boolean>
    lateinit var choicesPredicate: MutableState<(List<Int>) -> Boolean>
    lateinit var intRange: MutableState<IntRange>
    lateinit var actions: MutableState<List<Action>>

    // Gameplay interaction responses
    lateinit var choice: MutableState<Int>
    lateinit var multiChoice: MutableState<List<Int>>
    lateinit var stringResponse: MutableState<String>
    lateinit var actionChoice: MutableState<Action>

    // Early initialization states
    lateinit var gameReady: MutableState<Boolean>

    // Early initialization non-states
    lateinit var emocioArgs: Array<String>
    lateinit var emocioArtifacts: List<Artifact>
}