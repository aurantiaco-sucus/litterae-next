package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.common.LazilyIdentifiedAny
import xyz.midnight233.litterae.common.ThislessReadOnlyProperty

class Segment(val builder: Segment.() -> Unit) {
    private lateinit var scenes: MutableList<Scene>
    private lateinit var notes: MutableList<Note>

    lateinit var entryScene: Scene

    val identity by lazy { this::class.qualifiedName }

    fun build() = builder(this)

    fun scene(name: String, builder: Scene.() -> Unit) : ThislessReadOnlyProperty<Scene> {
        val scene = Scene(name, builder)
        scenes += scene
        return LazilyIdentifiedAny.referenceOf(scene)
    }

    fun note(title: String, category: NoteCategory, content: String) : ThislessReadOnlyProperty<Note> {
        val note = Note(title, category, content)
        notes += note
        return LazilyIdentifiedAny.referenceOf(note)
    }

    fun mark() = Mark { prop -> "Chapter(${identity}):Local:Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Chapter(${identity}):Local:Memo(${prop.name})" }
}