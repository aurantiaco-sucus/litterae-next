package xyz.midnight233.emocio.implementation

import xyz.midnight233.litterae.runtime.Instance
import xyz.midnight233.litterae.runtime.NoteData
import java.io.File

object EmocioBackend {
    val engineDir = File(".\\.litterae")
    val profileDir by lazy { File(".\\.litterae\\${EmocioRuntime.artifact.identifier}") }

    fun checkSanity(): Boolean {
        if (!profileDir.isDirectory) return false
        return true
    }

    fun fixSanity(): Boolean {
        if (checkSanity()) return true
        if (!engineDir.isDirectory && engineDir.exists()) return false
        if (!engineDir.exists()) engineDir.mkdir()
        if (!profileDir.isDirectory && profileDir.exists()) return false
        if (!profileDir.exists()) profileDir.mkdir()
        return true
    }

    fun deprecateFile(file: File) {
        val old = file.resolveSibling("${file.name}.old")
        if (old.exists()) old.delete()
        if (file.exists()) file.renameTo(old)
    }

    fun createInstance(name: String) = object : Instance() {
        override var instanceName: String = name

        override fun save() {
            TODO("Not yet implemented")
        }

        override fun load() {
            TODO("Not yet implemented")
        }

        override val marks: MutableList<String> = mutableListOf()
        override val memos: MutableMap<String, String> = mutableMapOf()
        override val notes: MutableList<NoteData> = mutableListOf()
    }
}