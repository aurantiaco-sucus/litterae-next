package xyz.midnight233.emocio.stateful

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import xyz.midnight233.litterae.content.Artifact

object EmocioState {
    lateinit var stateType: MutableState<StateType>
    lateinit var journalSize: MutableState<Int>
    lateinit var journal: SnapshotStateList<JournalEntry>
    lateinit var candidates: MutableState<List<String>>
    lateinit var stringPredicate: MutableState<(String) -> Boolean>
    lateinit var choicesPredicate: MutableState<(List<Int>) -> Boolean>
    lateinit var intRange: MutableState<IntRange>
    lateinit var choice: MutableState<Int>
    lateinit var choices: MutableState<List<Int>>
    lateinit var response: MutableState<String>

    var emocioReady = false
    lateinit var emocioArgs: Array<String>
    lateinit var emocioArtifacts: List<Artifact>
}