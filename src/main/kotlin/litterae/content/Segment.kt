package litterae.content

import litterae.common.Reference
import litterae.common.References.weakReferenceOf

abstract class Segment(val builder: Segment.() -> Unit) {
    lateinit var scenes: MutableList<Scene>
    lateinit var notes: MutableList<Note>

    val identifier by lazy { this::class.qualifiedName }

    fun build() {
        scenes = mutableListOf()
        notes = mutableListOf()
        builder(this)
    }

    fun scene(name: String, builder: Scene.() -> Unit): Reference<Scene> {
        val scene = Scene(this, scenes.lastIndex + 1, name, builder)
        scenes += scene
        return scenes.weakReferenceOf(scenes.lastIndex)
    }

    fun note(title: String, category: NoteCategory = NoteCategory.Storyline, content: String) : Reference<Note> {
        val note = Note("Segment(${this.identifier}):Note(${notes.lastIndex + 1})", title, category, content)
        notes += note
        return notes.weakReferenceOf(notes.lastIndex)
    }

    fun mark() = Mark { prop -> "Segment(${identifier}):Local:Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${identifier}):Local:Memo(${prop.name})" }
}