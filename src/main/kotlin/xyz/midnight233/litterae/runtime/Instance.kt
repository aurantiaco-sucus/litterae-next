package xyz.midnight233.litterae.runtime

import xyz.midnight233.litterae.content.Memo

abstract class Instance {
    abstract var instanceName: String
    abstract fun save()
    abstract fun load()
    abstract val marks: MutableList<String>
    abstract val memos: MutableMap<String, String>
    abstract val notes: MutableList<NoteData>

    var currentSegmentIdentifier by Memo { "Global:CurrentSegment" }
    var currentSceneIndex by Memo { "Global:CurrentScene" }

    companion object {
        lateinit var current: Instance
    }
}