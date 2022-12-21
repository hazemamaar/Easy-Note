package com.android.easynote.domain

import android.util.Log
import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.Repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllNotesUseCase(private val localRepo: LocalRepo):BaseLocalUseCase<List<NoteDto>,List<NoteDto>,Any>() {

    //    operator fun invoke() = flow<List<GetNotesModel>> {
//        emit(GetAllNotes().map(localRepo.getAllNotes()))
//    }
    override fun mapper(req: List<NoteDto>): List<NoteDto> {
        return req
    }

    override suspend fun run(params: Any?): Flow<List<NoteDto>> = flow {
        Log.e("run", "run: "+localRepo.getAllNotes() )
        emit(localRepo.getAllNotes())
    }
    override var isSave: Boolean =false
    override suspend fun saveToLocal(res: List<NoteDto>): List<NoteDto> {
        TODO("Not yet implemented")
    }


}