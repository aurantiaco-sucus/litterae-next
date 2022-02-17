package xyz.midnight233.litterae.runtime

import xyz.midnight233.litterae.content.NoteCategory

object InstanceCodec {
    fun encodeMarks(source: List<String>): String = source
        .joinToString(separator = System.getProperty("line.separator"))

    fun decodeMarks(source: String): List<String> = source
        .lines()
        .filter { it.isNotEmpty() }

    fun encodeMemos(source: Map<String, String>): String = source
        .map { "${it.key}::${it.value}" }
        .joinToString(separator = System.getProperty("line.separator"))

    fun decodeMemos(source: String): Map<String, String> = source
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.split("::") }
        .associate { it[0] to it[1] }

    fun encodeNotes(source: List<NoteData>): String = source
        .joinToString(separator = System.getProperty("line.separator"))
        { "${it.identifier}::${it.title}::${it.category.name}::${it.content}" }

    fun decodeNotes(source: String): List<NoteData> = source
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.split("::") }
        .map { NoteData(it[0], it[1], NoteCategory.valueOf(it[2]), it[3]) }
}