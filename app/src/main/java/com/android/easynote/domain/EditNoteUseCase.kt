package com.android.easynote.domain

import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditNoteUseCase(private val localRepo: LocalRepo) : BaseLocalUseCase<NoteDto,Int,NoteDto>() {
    override fun mapper(req: NoteDto): Int {
        TODO("Not yet implemented")
    }

    override suspend fun run(params: NoteDto?): Flow<NoteDto> =flow{
        emit(NoteDto(1))
    }

    override var isSave: Boolean=true
    override suspend fun saveToLocal(res: NoteDto): Int =localRepo.updateNote(res)
}