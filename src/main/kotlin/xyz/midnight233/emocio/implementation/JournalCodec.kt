package xyz.midnight233.emocio.implementation

import xyz.midnight233.emocio.stateful.JournalEntry
import xyz.midnight233.emocio.stateful.JournalEntryType

object JournalCodec {
    fun encode(entry: JournalEntry) = "${entry.type.name}::${entry.content}"

    fun encode(entries: List<JournalEntry>) =
        entries.joinToString(separator = System.getProperty("line.separator")) { encode(it) }

    fun decode(line: String) =
        line.split("::").let { JournalEntry(JournalEntryType.valueOf(it[0]), it[1]) }

    fun decode(lines: List<String>) = lines.map { decode(it) }
}