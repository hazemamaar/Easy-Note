package com.android.easynote.domain

import com.android.easynote.core.base.BaseMapper
import com.android.easynote.data.entities.NoteDto

class GetAllNotes : BaseMapper<NoteDto,GetNotesModel>() {
    override fun map(from: NoteDto): GetNotesModel = GetNotesModel(id = from.id
        , title = from.title,
    noteText = from.noteText, pin = from.pin, lock = from.lock,from.color)

}