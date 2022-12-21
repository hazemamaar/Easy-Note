package com.android.easynote.data.Repo

import com.android.easynote.data.entities.NoteDto
import com.android.easynote.data.local.NoteDao

class LocalRepo (private val noteDao: NoteDao) {
    suspend fun getAllNotes()=noteDao.getAllNotes()
    suspend fun insertNote(note: NoteDto)=noteDao.insertNotes(note = note)
    suspend fun updateNote(note: NoteDto) {
        noteDao.updateNotes(note)
    }
}