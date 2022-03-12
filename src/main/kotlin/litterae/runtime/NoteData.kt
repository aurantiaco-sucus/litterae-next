package litterae.runtime

import litterae.content.NoteCategory

data class NoteData(val identifier: String, val title: String, val category: NoteCategory, val content: String) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is NoteData) return false
        return hashCode() == other.hashCode()
    }
    override fun hashCode(): Int {
        return identifier.hashCode()
    }
}
