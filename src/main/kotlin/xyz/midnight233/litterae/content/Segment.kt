package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.common.LazilyIdentifiedAny
import xyz.midnight233.litterae.common.ThislessReadOnlyProperty

class Segment(val builder: Segment.() -> Unit) {
    lateinit var scenes: MutableList<Scene>
    lateinit var notes: MutableList<Note>

    lateinit var entryScene: Scene

    val identity by lazy { this::class.qualifiedName }

    fun build() = builder(this)

    fun scene(name: String, builder: Scene.() -> Unit) : ThislessReadOnlyProperty<Scene> {
        val scene = Scene(this, name, builder)
        scenes += scene
        return LazilyIdentifiedAny.referenceOf(scene)
    }

    fun note(title: String, category: NoteCategory, content: String) : ThislessReadOnlyProperty<Note> {
        val note = Note(this, title, category, content)
        notes += note
        return LazilyIdentifiedAny.referenceOf(note)
    }

    fun mark() = Mark { prop -> "Segment(${identity}):Local:Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${identity}):Local:Memo(${prop.name})" }
}