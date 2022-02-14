package xyz.midnight233.litterae.content

import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.litterae.runtime.Instance
import xyz.midnight233.litterae.runtime.NoteData

class Note(
    val segment: Segment,
    val index: Int,
    val title: String,
    val category: NoteCategory,
    val content: String
) {
    val data by lazy { NoteData(title, category, content) }
    private var availabilityMark by Mark { "Chapter(${segment.identifier}):Scene(${index}):Memo" }

    var available get() = availabilityMark
        set(value) {
            if (value && !availabilityMark) {
                Instance.current.notes += data
                EmocioState.notebookSize.value++
            } else if (!value && availabilityMark) {
                Instance.current.notes -= data
                EmocioState.notebookSize.value--
            }
            availabilityMark = value
        }
}