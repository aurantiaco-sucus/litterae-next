package xyz.midnight233.emocio.implementation

import xyz.midnight233.litterae.runtime.Instance
import xyz.midnight233.litterae.runtime.InstanceCodec
import xyz.midnight233.litterae.runtime.NoteData
import java.io.File
import kotlin.system.exitProcess

object EmocioBackend {
    val engineDir = File(".litterae")
    val profileDir get() = File(".litterae/${EmocioRuntime.artifact.identifier}")

    const val fileExtension = "litterae_data"

    fun composeBackendInit() {
        if (!checkSanity()) {
            print("Backend reported bad sanity! Fixing...")
            if (fixSanity()) {
                println("Done.")
            } else {
                println("Failed!\n")
                println("FATAL ERROR: Emocio backend can't access the storage space!\n" +
                    "* There could be a file with the same name of storage folder:\n" +
                    "  .litterae/${EmocioRuntime.artifact.identifier}\n" +
                    "* The storage folder could be unreachable for Emocio.\n" +
                    "* Some JVM or OS related I/O problem occurred.")
                exitProcess(-1)
            }
        }
    }

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

    fun deprecateFile(fileName: String) = deprecateFile(File(fileName))

    fun checkInstanceName(name: String): Boolean {
        name.forEach {
            when (it) {
                ';', ':', '+', '\'', '"', ',', '.', '/', '\\',
                '?', '~', '!', '@', '#', '$', '%', '^', '&', '*',
                '(', ')', '=', '{', '}', '[', ']', '|', '<', '>' -> return false
                else -> {}
            }
        }
        return true
    }

    val instances: List<Instance> get() {
        val list = profileDir.list()
        return list!!.filter { File("${profileDir.path}/$it").isDirectory }.map { instanceNamed(it) }
    }

    fun instanceNamed(name: String) = object : Instance() {
        override var instanceName: String = name

        val instanceFolder = ".litterae/${EmocioRuntime.artifact.identifier}/$instanceName"

        fun deprecateAll() =
                listOf("memo", "mark", "note")
                        .forEach { deprecateFile("$instanceFolder/$it.$fileExtension") }

        override fun save() {
            if (!File(instanceFolder).exists()) File(instanceFolder).mkdir()
            deprecateAll()
            File("$instanceFolder/mark.$fileExtension").writeText(InstanceCodec.encodeMarks(marks))
            File("$instanceFolder/memo.$fileExtension").writeText(InstanceCodec.encodeMemos(memos))
            File("$instanceFolder/note.$fileExtension").writeText(InstanceCodec.encodeNotes(notes))
        }

        override fun load() {
            marks.clear()
            marks.addAll(InstanceCodec.decodeMarks(File("$instanceFolder/mark.$fileExtension").readText()))
            memos.clear()
            memos.putAll(InstanceCodec.decodeMemos(File("$instanceFolder/memo.$fileExtension").readText()))
            notes.clear()
            notes.addAll(InstanceCodec.decodeNotes(File("$instanceFolder/note.$fileExtension").readText()))
        }

        override val marks: MutableList<String> = mutableListOf()
        override val memos: MutableMap<String, String> = mutableMapOf()
        override val notes: MutableList<NoteData> = mutableListOf()
    }

    fun Instance.exists(): Boolean {
        return File(".litterae/${EmocioRuntime.artifact.identifier}/$instanceName").exists()
    }
}