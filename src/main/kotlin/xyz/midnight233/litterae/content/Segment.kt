package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.common.LazilyIdentifiedAny
import xyz.midnight233.litterae.common.Reference
import xyz.midnight233.litterae.common.References.weakReferenceOf
import xyz.midnight233.litterae.common.ThislessReadOnlyProperty

abstract class Segment(val builder: Segment.() -> Unit) {
    lateinit var scenes: MutableList<Scene>
    lateinit var notes: MutableList<Note>

    val identifier by lazy { this::class.qualifiedName }

    fun build() = builder(this)

    fun scene(name: String, builder: Scene.() -> Unit): Reference<Scene> {
        val scene = Scene(this, scenes.lastIndex + 1, name, builder)
        scenes += scene
        return scenes.weakReferenceOf(scenes.lastIndex)
    }

    fun note(title: String, category: NoteCategory, content: String) : Reference<Note> {
        val note = Note(this, notes.lastIndex + 1, title, category, content)
        notes += note
        return notes.weakReferenceOf(notes.lastIndex)
    }

    fun mark() = Mark { prop -> "Segment(${identifier}):Local:Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${identifier}):Local:Memo(${prop.name})" }
}