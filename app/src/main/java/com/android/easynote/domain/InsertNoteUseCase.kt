package com.android.easynote.domain

import androidx.core.text.PrecomputedTextCompat.Params
import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.Repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertNoteUseCase(private val localRepo: LocalRepo) :BaseLocalUseCase<NoteDto,Long,NoteDto>() {
    override fun mapper(req: NoteDto): Long {
        TODO("Not yet implemented")
    }
    override var isSave: Boolean =true
    override suspend fun saveToLocal(res: NoteDto): Long = localRepo.insertNote(res)
    override suspend fun run(params: NoteDto?): Flow<NoteDto> =flow{
        emit(NoteDto(1))
    }


}