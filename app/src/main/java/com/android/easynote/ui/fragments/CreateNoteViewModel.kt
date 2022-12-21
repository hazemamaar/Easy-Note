package com.android.easynote.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.data.local.NoteDao
import com.android.easynote.domain.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CreateNoteViewModel(private val useCase: InsertNoteUseCase) :ViewModel() {
    private val nextAction = MutableSharedFlow<Long>()
    val viewState: SharedFlow<Long> = nextAction
    fun createNote(noteDto: NoteDto){
        useCase.invoke(noteDto,viewModelScope){
            viewModelScope.launch {
            nextAction.emit(it)}
        }

    }
}