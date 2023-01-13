package com.android.easynote.core.helpers

import com.android.easynote.data.entities.NoteDto

interface SortNotes {

    fun sortNotesByPin(notes: List<NoteDto>) :List<NoteDto>
}