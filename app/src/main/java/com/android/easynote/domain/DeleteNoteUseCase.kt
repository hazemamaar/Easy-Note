package com.android.easynote.domain

import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.data.Repo.LocalRepo
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNoteUseCase(private val localRepo: LocalRepo) : BaseLocalUseCase<Int,Int,Int>() {
    override fun mapper(req: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun run(params: Int?): Flow<Int> = flow {
        emit(0)
    }

    override var isSave: Boolean=true

    override suspend fun saveToLocal(res: Int): Int = localRepo.delete(res)

}