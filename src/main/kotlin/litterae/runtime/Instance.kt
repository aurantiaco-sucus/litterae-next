package litterae.runtime

import litterae.content.Memo
import java.util.concurrent.atomic.AtomicBoolean

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
        val instanceReady = AtomicBoolean(false)
        lateinit var current: Instance
    }
}