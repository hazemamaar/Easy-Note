package com.android.easynote.domain


import com.android.easynote.core.base.BaseLocalUseCase
import com.android.easynote.core.extention.log
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.data.repo.LocalRepo
import kotlinx.coroutines.flow.Flow


class SearchUseCase (private val localRepo: LocalRepo) :
    BaseLocalUseCase<List<NoteDto>, List<NoteDto>, String>() {

    override fun mapper(req: List<NoteDto>): List<NoteDto> {
        return req
    }

    override suspend fun run(params: String?): Flow<List<NoteDto>> {
        params.toString().log("hazemammar")
        return localRepo.searchByTitleOrDescription(params as String)
    }


    override var isSave: Boolean = false
    override suspend fun saveToLocal(res: List<NoteDto>): List<NoteDto> {
        TODO("Not yet implemented")
    }


}