package com.android.easynote.domain

import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GetAllNotesUseCase(private val localRepo: LocalRepo) :
    BaseLocalUseCase<List<NoteDto>, List<NoteDto>, Any>() {

    override fun mapper(req: List<NoteDto>): List<NoteDto> {
        return req
    }

    override suspend fun run(params: Any?): Flow<List<NoteDto>> =
       localRepo.getAllNotes()



    override var isSave: Boolean = false
    override suspend fun saveToLocal(res: List<NoteDto>): List<NoteDto> {
        TODO("Not yet implemented")
    }


}