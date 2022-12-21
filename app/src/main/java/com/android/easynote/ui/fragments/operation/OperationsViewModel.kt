package com.android.easynote.ui.fragments.operation


import androidx.lifecycle.viewModelScope
import com.android.easynote.core.base.BaseViewModel
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.domain.EditNoteUseCase
import com.android.easynote.domain.InsertNoteUseCase

class OperationsViewModel(private val insertUseCase: InsertNoteUseCase, private val editUseCase: EditNoteUseCase ) :BaseViewModel<OperationsAction>() {
    fun createNote(noteDto: NoteDto){

        insertUseCase.invoke(noteDto,viewModelScope){
            produce(OperationsAction.CreateNote(it))
        }
    }
    fun editeNote(noteDto: NoteDto){
        editUseCase.invoke(noteDto,viewModelScope){
            produce(OperationsAction.EditNote(it))
        }
    }
}