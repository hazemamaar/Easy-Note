package com.android.easynote.domain

import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.Repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllNotesUseCase(private val localRepo: LocalRepo):BaseLocalUseCase<List<NoteDto>,List<GetNotesModel>,Any>() {

    //    operator fun invoke() = flow<List<GetNotesModel>> {
//        emit(GetAllNotes().map(localRepo.getAllNotes()))
//    }
    override fun mapper(req: List<NoteDto>): List<GetNotesModel> = GetAllNotes().map(req)

    override suspend fun run(params: Any?): Flow<List<NoteDto>> = flow {
        emit(localRepo.getAllNotes())
    }
    override var isSave: Boolean =false
    override suspend fun saveToLocal(res: List<NoteDto>): List<GetNotesModel> {
        TODO("Not yet implemented")
    }


}