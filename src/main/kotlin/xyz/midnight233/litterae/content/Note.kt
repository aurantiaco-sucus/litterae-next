package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.common.LazilyIdentifiedAny
import xyz.midnight233.litterae.runtime.NoteData

class Note(
    val segment: Segment,
    val title: String,
    val category: NoteCategory,
    val content: String
) : LazilyIdentifiedAny() {
    val data by lazy { NoteData(title, category, content) }
    var available by Mark { "Chapter(${segment.identity}):Scene(${objectIdentifier}):Memo" }
}