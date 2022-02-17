package xyz.midnight233.litterae.content

import xyz.midnight233.emocio.stateful.EmocioState
import xyz.midnight233.litterae.runtime.Instance
import xyz.midnight233.litterae.runtime.NoteData

class Note(
    val markPath: String,
    val title: String,
    val category: NoteCategory,
    val content: String
) {
    private val data by lazy { NoteData(markPath, title, category, content) }
    private var availabilityMark by Mark { markPath }

    var available get() = availabilityMark
        set(value) {
            if (value && !availabilityMark) {
                Instance.current.notes += data
                EmocioState.notebookSize.value++
            } else if (!value && availabilityMark) {
                Instance.current.notes.removeAll { it == data }
                EmocioState.notebookSize.value--
            }
            availabilityMark = value
        }
}