package litterae.content

import litterae.common.Reference
import litterae.common.References.weakReferenceOf
import litterae.compose.CompositionLambda

class Scene(val segment: Segment, private val index: Int, val name: String, val builder: Scene.() -> Unit) {
    var immediateEvent: CompositionLambda? = null
    lateinit var actions: MutableList<Action>
    lateinit var notes: MutableList<Note>

    fun rebuild() {
        immediateEvent = null
        actions = mutableListOf()
        notes = mutableListOf()
        builder(this)
    }

    fun immediate(content: CompositionLambda) {
        immediateEvent = content
    }

    fun action(
        name: String,
        category: ActionCategory = ActionCategory.Context,
        description: String? = null,
        content: CompositionLambda
    ) {
        actions += Action(name, category, description, content)
    }

    fun note(title: String, category: NoteCategory = NoteCategory.Storyline, content: String) : Reference<Note> {
        val note = Note(
            "Segment(${segment.identifier}):Scene($index):Note(${notes.lastIndex + 1})"
            , title, category, content)
        notes += note
        return notes.weakReferenceOf(notes.lastIndex)
    }

    fun mark() = Mark { prop -> "Segment(${segment.identifier}):Scene(${index}):Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${segment.identifier}):Scene(${index}):Memo(${prop.name})" }
}