package com.android.easynote.data.repo

import androidx.lifecycle.asLiveData
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.data.local.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LocalRepo(private val noteDao: NoteDao) {
    fun getAllNotes() = noteDao.getAllNotes()
    suspend fun insertNote(note: NoteDto) = noteDao.insertNotes(note = note)
    suspend fun updateNote(note: NoteDto) = noteDao.updateNotes(note)
    suspend fun delete(id: Int) = noteDao.deleteSpecificNote(id)

    fun searchByTitleOrDescription(searchQuery:String) =
        noteDao.searchByTitleOrDescription(searchQuery)





}