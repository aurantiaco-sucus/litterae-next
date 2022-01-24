package xyz.midnight233.litterae.runtime

abstract class Instance {
    abstract var instanceName: String
    abstract fun save()
    abstract fun load()
    abstract val marks: MutableList<String>
    abstract val memos: MutableMap<String, String>
    abstract val notes: MutableList<NoteData>

    companion object {
        lateinit var current: Instance
    }
}