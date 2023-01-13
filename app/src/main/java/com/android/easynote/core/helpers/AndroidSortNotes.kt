package com.android.easynote.core.helpers

import com.android.easynote.data.entities.NoteDto

class AndroidSortNotes : SortNotes {

    override fun sortNotesByPin(notes: List<NoteDto>): List<NoteDto> {
        return notes.toMutableList().sortedByDescending { it.pin }.toMutableList()
    }
}